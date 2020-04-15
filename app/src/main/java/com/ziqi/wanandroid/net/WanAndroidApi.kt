package com.ziqi.wanandroid.net

import com.ziqi.wanandroid.bean.Banner
import com.ziqi.wanandroid.bean.WanResponse
import com.ziqi.wanandroid.constant.UrlConstant
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

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
    @GET(UrlConstant.url_banner)
    suspend fun banner(): WanResponse<List<Banner>>
}