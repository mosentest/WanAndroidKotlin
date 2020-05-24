package com.ziqi.wanandroid.commonlibrary.net

import android.os.SystemClock
import com.ziqi.baselibrary.http.error.ResponseThrowable
import com.ziqi.baselibrary.util.GsonUtil
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.wanandroid.commonlibrary.bean.WanResponse
import okhttp3.*
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import okio.BufferedSource
import java.io.IOException
import java.nio.charset.Charset

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/24 4:26 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class WanAndroidInterceptor : Interceptor {
    private val TAG = WanAndroidInterceptor::class.java.simpleName

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val method = request.method()
        if ("POST" == method) {
            val body = request.body()
            if (body is FormBody) {
                //https://blog.csdn.net/wenyingzhi/article/details/80510249
                val builder = FormBody.Builder()
                for (i in 0 until body.size()) {
                    builder.add(body.encodedName(i), body.encodedValue(i))
                    LogUtil.i(
                        TAG,
                        Thread.currentThread().name + ">>key:" + body.encodedName(i) + ",value:" + body.encodedValue(
                            i
                        )
                    )
                }
                //增加参数或者加密在这里操作
                builder.add("time", SystemClock.elapsedRealtime().toString())
                //构造新的请求体,覆盖之前的body
                val newRequestBody = builder.build()
                request = request.newBuilder().post(newRequestBody).build()
            } else if (body is MultipartBody) {
                //后面再考虑
                var builder = MultipartBody.Builder()
            }
        }
        var response = chain.proceed(request)
        if (HttpHeaders.hasBody(response)) {
            val responseBody = response.body()
            responseBody?.apply {
                val source: BufferedSource = source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                val buffer: Buffer = source.buffer()
                val charset = Charset.forName("UTF-8")
                val content = buffer.clone().readString(charset)

                //===打印返回内容，如果解密之类操作，也可以在这里操作
                LogUtil.i(
                    TAG,
                    Thread.currentThread().name + ">>content:" + content
                )
                //===打印返回内容，如果解密之类操作，也可以在这里操作

                val obj = GsonUtil.gson.fromJson<WanResponse<Any>>(content, WanResponse::class.java)
                if (obj.errorCode != 0) {
                    throw ResponseThrowable(obj.errorCode + 1, obj.errorMsg)
                }
                val newResponseBody = ResponseBody.create(contentType(), content)
                response = response.newBuilder().body(newResponseBody).build()
            }

        }
        return response
    }
}