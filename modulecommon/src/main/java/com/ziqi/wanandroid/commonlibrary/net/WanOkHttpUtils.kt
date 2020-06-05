package com.ziqi.wanandroid.commonlibrary.net

import android.content.Context
import com.ziqi.baselibrary.http.cookie.MyCookieJarImpl
import com.ziqi.baselibrary.util.ContextUtils
import com.ziqi.baselibrary.util.SystemTool
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/9 9:41 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class WanOkHttpUtils {

    var okHttpClient: OkHttpClient

    private constructor(context: Context) {
        /**
         * https://www.jianshu.com/p/dbda0bb8d541
         * 有网时候的缓存
         */
        val netCacheInterceptor = Interceptor { chain ->
            val request: Request = chain.request()
            val response: Response = chain.proceed(request)
            val onlineCacheTime = 1 * 60 * 60  //在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$onlineCacheTime")
                .removeHeader("Pragma")
                .build()
        }
        /**
         * 没有网时候的缓存
         */
        val offlineCacheInterceptor = Interceptor { chain ->
            var request = chain.request()
            if (SystemTool.checkNet(ContextUtils.context) == SystemTool.NETWORK_NONE) {
                val offlineCacheTime = 3 * 60 * 60  //离线的时候的缓存的过期时间
                request = request.newBuilder()
                    .header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=$offlineCacheTime"
                    )
                    .build()
            }
            chain.proceed(request)
        }
        val httpCacheDirectory = File(context.getCacheDir(), "okhttpCache")
        okHttpClient = OkHttpClient().newBuilder()
            .addNetworkInterceptor(WanAndroidInterceptor())
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(netCacheInterceptor)
            .addInterceptor(offlineCacheInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .cookieJar(MyCookieJarImpl(context))
            .retryOnConnectionFailure(false)
            .cache(Cache(httpCacheDirectory, 1 * 1024 * 1024))
            .build()
    }

    companion object {
        //https://www.jb51.net/article/145255.htm
        val instance: WanOkHttpUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            WanOkHttpUtils(context = ContextUtils.context!!)
        }
    }
}