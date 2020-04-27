package com.ziqi.wanandroid.net

import com.ziqi.wanandroid.bean.Article
import com.ziqi.wanandroid.bean.Banner
import com.ziqi.wanandroid.bean.WanResponse
import com.ziqi.wanandroid.bean.WanList
import kotlinx.coroutines.Deferred

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

    suspend fun login(): Deferred<Any> {
        return RetrofitUtils.get().api.login()
    }

    suspend fun banner(): WanResponse<MutableList<Banner>> {
        return RetrofitUtils.get().api.banner()
    }

    suspend fun articleTop(): WanResponse<MutableList<Article>> {
        return RetrofitUtils.get().api.articleTop()
    }

    suspend fun articleList(pos: Int): WanResponse<WanList<Article>> {
        return RetrofitUtils.get().api.articleList(pos)
    }
}