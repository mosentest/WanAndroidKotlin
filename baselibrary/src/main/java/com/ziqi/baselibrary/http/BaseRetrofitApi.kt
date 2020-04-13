package com.ziqi.baselibrary.http

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface BaseRetrofitApi {

    @Streaming
    @GET
    fun down(@Url downUrl: String): Call<ResponseBody>

    @GET
    fun get(@Url url: String, @QueryMap maps: Map<String, Any>): Call<ResponseBody>

    @GET
    fun get(@Url url: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST
    fun post(@Url url: String, @FieldMap maps: Map<String, Any>): Call<ResponseBody>

    @POST
    fun post(@Url url: String, @Body body: RequestBody): Call<ResponseBody>

}