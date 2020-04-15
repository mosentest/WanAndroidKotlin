package com.ziqi.wanandroid.net

import com.ziqi.wanandroid.bean.Banner
import com.ziqi.wanandroid.bean.WanResponse
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/12 6:08 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object NetRepository : WanAndroidApi {

    override fun login(): Deferred<Any> {
        return RetrofitUtils.get().api.login()
    }

    override suspend fun banner(): WanResponse<List<Banner>> {
        return RetrofitUtils.get().api.banner()
    }
}