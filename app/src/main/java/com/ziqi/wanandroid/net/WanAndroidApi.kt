package com.ziqi.wanandroid.net

import com.ziqi.wanandroid.bean.*
import com.ziqi.wanandroid.constant.UrlConstant
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/15 11:14 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
interface WanAndroidApi {

    /**
     * 旧方式
     */
    fun login(): Deferred<Any>

    /**
     * 2.6.0 新方式
     */
    @GET("banner/json")
    suspend fun banner(): WanResponse<MutableList<Banner>>

    @GET("article/top/json")
    suspend fun articleTop(): WanResponse<MutableList<Article>>

    @GET("article/list/{pos}/json")
    suspend fun articleList(@Path("pos") pos: Int): WanResponse<WanList<Article>>

    @GET("tree/json")
    suspend fun tree(): WanResponse<MutableList<Tree>>

    @GET("article/listproject/{pos}/json")
    suspend fun listproject(@Path("pos") pos: Int): WanResponse<ListProject>
}