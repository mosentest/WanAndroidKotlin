package com.ziqi.wanandroid.net

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
object NetRepository {

    fun a() {
        RetrofitUtils.get().api.getByCall("").enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }

        })
    }
}