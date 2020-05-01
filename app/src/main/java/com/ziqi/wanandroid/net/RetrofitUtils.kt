package com.ziqi.wanandroid.net

import com.ziqi.baselibrary.http.OkHttpUtils
import com.ziqi.baselibrary.http.retrofit.StringConverterFactory
import com.ziqi.baselibrary.util.GsonUtil
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


    var wanAndroidApi: WanAndroidApi

    private constructor() {
        //val contentType = "application/json".toMediaType()
        //var contentType = MediaType.parse("application/json; charset=utf-8")
        val retrofit = Retrofit.Builder()
            .baseUrl(UrlConstant.host)
            .addConverterFactory(StringConverterFactory())
            //retrofit2-kotlinx-serialization-converter 暂时不知道干嘛用
            //.addConverterFactory(Json.asConverterFactory(contentType!!))
            .addConverterFactory(GsonConverterFactory.create(GsonUtil.gson))
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            // Please migrate to Retrofit 2.6.0 or newer and its built-in suspend support
            //https://github.com/square/retrofit/blob/master/CHANGELOG.md#version-260-2019-06-05
            //.addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(OkHttpUtils.instance.okHttpClient)
            .build()
        wanAndroidApi = retrofit.create(WanAndroidApi::class.java)
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