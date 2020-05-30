package com.ziqi.wanandroid.commonlibrary.util.glide;

import android.text.TextUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProgressManager {

    private final static WeakHashMap<String, OnProgressListener> LISTENERS_MAP = new WeakHashMap<>();

    private volatile static OkHttpClient okHttpClient;

    private ProgressManager() {
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (ProgressManager.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .addNetworkInterceptor(chain -> {
                                Request request = chain.request();
                                Response response = chain.proceed(request);
                                return response.newBuilder()
                                        .body(new ProgressResponseBody(request.url().toString(), LISTENER, response.body()))
                                        .build();
                            })
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    private static final ProgressResponseBody.InternalProgressListener LISTENER = (url, bytesRead, totalBytes) -> {
        OnProgressListener onProgressListener = getProgressListener(url);
        if (onProgressListener != null) {
            int percentage = (int) ((bytesRead * 1f / totalBytes) * 100f);
            boolean isComplete = percentage >= 100;
            onProgressListener.onProgress(isComplete, percentage, bytesRead, totalBytes);
            if (isComplete) {
                removeListener(url);
            }
        }
    };

    public synchronized static void addListener(String url, OnProgressListener listener) {
        if (!TextUtils.isEmpty(url) && listener != null) {
            LISTENERS_MAP.put(url, listener);
            listener.onProgress(false, 1, 0, 0);
        }
    }

    public synchronized static void removeListener(String url) {
        if (!TextUtils.isEmpty(url)) {
            LISTENERS_MAP.remove(url);
        }
    }

    public synchronized static OnProgressListener getProgressListener(String url) {
        if (TextUtils.isEmpty(url) || LISTENERS_MAP.size() == 0) {
            return null;
        }
        OnProgressListener listenerWeakReference = LISTENERS_MAP.get(url);
        if (listenerWeakReference != null) {
            return listenerWeakReference;
        }
        return null;
    }
}