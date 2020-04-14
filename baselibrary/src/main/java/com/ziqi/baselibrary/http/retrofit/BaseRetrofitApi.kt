package com.ziqi.baselibrary.http.retrofit

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface BaseRetrofitApi {

    @Streaming
    @GET
    fun downByCall(@Url downUrl: String): Call<ResponseBody>

    @GET
    fun getByCall(@Url url: String, @QueryMap maps: Map<String, Any>): Call<ResponseBody>

    @GET
    fun getByCall(@Url url: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST
    fun postByCall(@Url url: String, @FieldMap maps: Map<String, Any>): Call<ResponseBody>

    @POST
    fun postByCall(@Url url: String, @Body body: RequestBody): Call<ResponseBody>

}