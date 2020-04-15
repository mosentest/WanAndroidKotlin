package com.ziqi.wanandroid.net

import com.ziqi.baselibrary.http.retrofit.BaseRetrofitApi
import kotlinx.coroutines.Deferred

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/10 2:06 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
interface WanAndroidService : BaseRetrofitApi {
    fun login(): Deferred<Any>
}