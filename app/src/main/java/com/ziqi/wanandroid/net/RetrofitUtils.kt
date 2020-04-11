package com.ziqi.wanandroid.net

import com.ziqi.baselibrary.http.OkHttpUtils
import com.ziqi.wanandroid.constant.UrlConstant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/10 11:04 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class RetrofitUtils {

    private constructor() {
        val retrofit = Retrofit.Builder()
            .baseUrl(UrlConstant.host)
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpUtils.instance.okHttpClient)
            .build()
        retrofit.create(WanAndroidService::class.java)
    }

    companion object {

        @Volatile
        var instance: RetrofitUtils? = null

        fun get(): RetrofitUtils {
            if (instance == null) {
                synchronized(RetrofitUtils::class) {
                    if (instance == null) {
                        instance = RetrofitUtils()
                    }
                }
            }
            return instance!!
        }
    }
}