package com.ziqi.wanandroid.net

import com.ziqi.wanandroid.bean.*
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
        return RetrofitUtils.get().wanAndroidApi.login()
    }

    suspend fun banner(): WanResponse<MutableList<Banner>> {
        return RetrofitUtils.get().wanAndroidApi.banner()
    }

    suspend fun articleTop(): WanResponse<MutableList<Article>> {
        return RetrofitUtils.get().wanAndroidApi.articleTop()
    }

    suspend fun articleList(pos: Int): WanResponse<WanList<Article>> {
        return RetrofitUtils.get().wanAndroidApi.articleList(pos)
    }

    suspend fun listproject(pos: Int): WanResponse<ListProject> {
        return RetrofitUtils.get().wanAndroidApi.listproject(pos)
    }

    suspend fun tree(): WanResponse<MutableList<Tree>> {
        return RetrofitUtils.get().wanAndroidApi.tree()
    }

    suspend fun articleList(pos: Int, cid: Int): WanResponse<WanList<Article>> {
        return RetrofitUtils.get().wanAndroidApi.articleList(pos, cid)
    }
}