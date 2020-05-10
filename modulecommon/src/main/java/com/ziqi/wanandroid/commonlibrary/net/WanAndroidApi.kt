package com.ziqi.wanandroid.commonlibrary.net

import com.ziqi.wanandroid.commonlibrary.bean.*
import kotlinx.coroutines.Deferred
import retrofit2.http.*

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
//    fun login(): Deferred<Any>

    /**
     * 2.6.0 新方式
     */
    @GET("banner/json")
    suspend fun banner(): WanResponse<MutableList<Banner>>

    @GET("article/top/json")
    suspend fun articleTop(): WanResponse<MutableList<Article>>

    @GET("article/list/{pos}/json")
    suspend fun articleList(@Path("pos") pos: Int): WanResponse<WanList<Article>>

    @GET("article/listproject/{pos}/json")
    suspend fun listproject(@Path("pos") pos: Int): WanResponse<ListProject>

    @GET("tree/json")
    suspend fun tree(): WanResponse<MutableList<Tree>>

    @GET("article/list/{pos}/json")
    suspend fun articleList(@Path("pos") pos: Int, @Query("cid") cid: Int): WanResponse<WanList<Article>>

    @GET("project/tree/json")
    suspend fun projectTree(): WanResponse<MutableList<Tree>>

    @GET("project/list/{pos}/json")
    suspend fun project(@Path("pos") pos: Int, @Query("cid") cid: Int): WanResponse<ListProject>

    @GET("wxarticle/chapters/json")
    suspend fun wxArticleChapters(): WanResponse<MutableList<Tree>>

    @GET("wxarticle/list/{cid}/{pos}/json")
    suspend fun wxArticleList(@Path("cid") cid: Int, @Path("pos") pos: Int): WanResponse<ListProject>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@FieldMap map: Map<String, String>): WanResponse<Any>

    suspend fun login(@Field("username") username: String, @Field("password") password: String): WanResponse<Any>
}