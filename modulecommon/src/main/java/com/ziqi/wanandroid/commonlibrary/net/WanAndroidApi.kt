package com.ziqi.wanandroid.commonlibrary.net

import com.ziqi.wanandroid.commonlibrary.bean.*
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
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
    suspend fun login(@FieldMap map: Map<String, String>): WanResponse<User>

    @FormUrlEncoded
    @POST
    suspend fun <T> post(@Url url: String, @FieldMap maps: Map<String, String>): WanResponse<T>

    /**
     * 直接暴露username和password关键字，不安全
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): WanResponse<User>

    @GET("user/logout/json")
    suspend fun logout(): WanResponse<Any>


    /**
     * 收藏文章列表
     */
    @POST("lg/collect/list/{pos}/json")
    suspend fun lgCollectList(@Path("pos") pos: Int): WanResponse<ListProject>


    /**
     *收藏站内文章
     */
    @POST("lg/collect/{cid}/json")
    suspend fun lgCollect(@Path("cid") cid: Int?): WanResponse<Any>


    /**
     * 收藏站外文章
     */
    @FormUrlEncoded
    @POST("lg/collect/add/json")
    suspend fun lgCollectAdd(
        @Field("title") title: String?,
        @Field("author") author: String?,
        @Field("link") link: String?
    ): WanResponse<Any>


    /**
     *取消收藏
     *
     *
     */
    @POST("lg/uncollect_originId/{cid}/json")
    suspend fun lgUncollectOriginId(@Path("cid") cid: Int?): WanResponse<Any>

    /**
     * 我的收藏页面（该页面包含自己录入的内容）
     *
     * originId 代表的是你收藏之前的那篇文章本身的id； 但是收藏支持主动添加，这种情况下，没有originId则为-1
     */
    @POST("lg/uncollect/{cid}/json")
    suspend fun lgUncollect(@Path("cid") cid: Int, @Field("originId") originId: String): WanResponse<Any>

    /**
     * 收藏文章列表
     */
    @POST("lg/collect/usertools/json")
    suspend fun lgCollectUsertools(): WanResponse<ListProject>

    /**
     * 收藏网址
     */
    @POST("lg/collect/addtool/json")
    suspend fun lgCollectAddtool(@Field("name") name: String, @Field("link") link: String): WanResponse<Any>

    /**
     * 编辑收藏网站
     */
    @POST("lg/collect/updatetool/json")
    suspend fun lgCollectUpdatetool(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("link") link: String
    ): WanResponse<Any>

    /**
     * 删除收藏网站
     */
    @POST("lg/collect/deletetool/json")
    suspend fun lgCollectdeletetool(
        @Field("id") id: String
    ): WanResponse<Any>

    /**
     * 搜索
     */
    @POST("article/query/{pos}/json")
    suspend fun articleQuery(
        @Path("pos") pos: Int,
        @Field("k") k: String
    ): WanResponse<ListProject>

    /**
     * 获取个人积分，需要登录后访问
     */
    @POST("lg/coin/userinfo/json")
    suspend fun userinfo(): WanResponse<Any>

    /**
     * 广场列表数据
     */
    @POST("user_article/list/{pos}/json")
    suspend fun userArticleList(@Path("pos") pos: Int): WanResponse<ListProject>


    /**
     *  问答
     */
    @POST("wenda/list/{pos}/json")
    suspend fun wenda(@Path("pos") pos: Int): WanResponse<ListProject>


}