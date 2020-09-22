/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mo.bee.main.interceptor;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

import static okhttp3.internal.platform.Platform.INFO;

/**
 * An OkHttp interceptor which logs request and response information. Can be applied as an
 * {@linkplain OkHttpClient#interceptors() application interceptor} or as a {@linkplain
 * OkHttpClient#networkInterceptors() network interceptor}. <p> The format of the logs created by
 * this class should not be considered stable and may change slightly between releases. If you need
 * a stable logging format, use your own interceptor.
 */
public final class BeeHttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger {
        void log(String message);

        String convertImportLog(int type, String message);

        /**
         * A {@link Logger} defaults output appropriate for the current platform.
         */
        Logger DEFAULT = new Logger() {
            @Override
            public void log(String message) {
                Platform.get().log(INFO, message, null);
            }

            @Override
            public String convertImportLog(int type, String message) {
                return message;
            }
        };
    }

    public BeeHttpLoggingInterceptor() {
        this(Logger.DEFAULT);
    }

    public BeeHttpLoggingInterceptor(Logger logger) {
        this.logger = logger;
    }

    private final Logger logger;

    private volatile Set<String> headersToRedact = Collections.emptySet();

    public void redactHeader(String name) {
        Set<String> newHeadersToRedact = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        newHeadersToRedact.addAll(headersToRedact);
        newHeadersToRedact.add(name);
        headersToRedact = newHeadersToRedact;
    }

    private volatile Level level = Level.NONE;

    /**
     * Change the level at which this interceptor logs.
     */
    public BeeHttpLoggingInterceptor setLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Level level = this.level;

        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        StringBuilder strLog = new StringBuilder();

        boolean logBody = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        String requestStartMessage = "--> "
                + request.method()
                + ' ' + request.url()
                + (connection != null ? " " + connection.protocol() : "");
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        strLog.append(requestStartMessage);
        strLog.append("\n");

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    strLog.append("Content-Type: ").append(requestBody.contentType());
                    strLog.append("\n");
                }
                if (requestBody.contentLength() != -1) {
                    strLog.append("Content-Length: ").append(requestBody.contentLength());
                    strLog.append("\n");
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    logHeader(strLog, headers, i);
                }
            }

            if (!logBody || !hasRequestBody) {
                strLog.append("--> END ").append(request.method());
                strLog.append("\n");
            } else if (bodyHasUnknownEncoding(request.headers())) {
                strLog.append("--> END ").append(request.method()).append(" (encoded body omitted)");
                strLog.append("\n");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                strLog.append("");
                strLog.append("\n");
                if (isPlaintext(buffer)) {
                    if (charset != null) {
                        strLog.append("入参：");
                        strLog.append("\n");
                        strLog.append(logger.convertImportLog(0, buffer.readString(charset)));
                        strLog.append("\n");
                    }
                    strLog.append("--> END ")
                            .append(request.method())
                            .append(" (")
                            .append(requestBody.contentLength())
                            .append("-byte body)");
                    strLog.append("\n");
                } else {
                    strLog.append("--> END ")
                            .append(request.method())
                            .append(" (binary ")
                            .append(requestBody.contentLength())
                            .append("-byte body omitted)");
                    strLog.append("\n");
                }
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            strLog.append("<-- HTTP FAILED: ").append(e);
            strLog.append("\n");
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = 0;
        if (responseBody != null) {
            contentLength = responseBody.contentLength();
        }
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        strLog.append("<-- ")
                .append(response.code())
                .append(response.message().isEmpty() ? "" : ' ' + response.message()).append(' ')
                .append(response.request().url())
                .append(" (")
                .append(tookMs)
                .append("ms")
                .append(!logHeaders ? ", " + bodySize + " body" : "")
                .append(')');
        strLog.append("\n");

        if (logHeaders) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                logHeader(strLog, headers, i);
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
                strLog.append("<-- END HTTP");
                strLog.append("\n");
            } else if (bodyHasUnknownEncoding(response.headers())) {
                strLog.append("<-- END HTTP (encoded body omitted)");
                strLog.append("\n");
            } else {
                BufferedSource source = null;
                if (responseBody != null) {
                    source = responseBody.source();
                }
                if (source != null) {
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                }
                Buffer buffer = null;
                if (source != null) {
                    buffer = source.buffer();
                }

                Long gzippedLength = null;
                if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
                    if (buffer != null) {
                        gzippedLength = buffer.size();
                    }
                    GzipSource gzippedResponseBody = null;
                    try {
                        if (buffer != null) {
                            gzippedResponseBody = new GzipSource(buffer.clone());
                        }
                        buffer = new Buffer();
                        if (gzippedResponseBody != null) {
                            buffer.writeAll(gzippedResponseBody);
                        }
                    } finally {
                        if (gzippedResponseBody != null) {
                            gzippedResponseBody.close();
                        }
                    }
                }

                Charset charset = UTF8;
                MediaType contentType = null;
                if (responseBody != null) {
                    contentType = responseBody.contentType();
                }
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (buffer != null && !isPlaintext(buffer)) {
                    strLog.append("");
                    strLog.append("\n");
                    strLog.append("<-- END HTTP (binary ")
                            .append(buffer.size())
                            .append("-byte body omitted)");
                    strLog.append("\n");
                    return response;
                }

                if (contentLength != 0) {
                    strLog.append("");
                    strLog.append("\n");
                    if (buffer != null) {
                        if (charset != null) {
                            strLog.append("出参：");
                            strLog.append("\n");
                            strLog.append(logger.convertImportLog(1, buffer.clone().readString(charset)));
                            strLog.append("\n");
                        }
                    }
                }

                if (gzippedLength != null) {
                    strLog.append("<-- END HTTP (")
                            .append(buffer.size())
                            .append("-byte, ")
                            .append(gzippedLength)
                            .append("-gzipped-byte body)");
                    strLog.append("\n");
                } else {
                    if (buffer != null) {
                        strLog.append("<-- END HTTP (").append(buffer.size()).append("-byte body)");
                        strLog.append("\n");
                    }
                }
            }
        }
        logger.log(strLog.toString());
        return response;
    }

    private void logHeader(StringBuilder strLog, Headers headers, int i) {
        String value = headersToRedact.contains(headers.name(i)) ? "██" : headers.value(i);
        strLog.append(headers.name(i)).append(": ").append(value);
        strLog.append("\n");
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private static boolean bodyHasUnknownEncoding(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null
                && !contentEncoding.equalsIgnoreCase("identity")
                && !contentEncoding.equalsIgnoreCase("gzip");
    }
}
