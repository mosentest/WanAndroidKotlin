package com.ziqi.wanandroid.commonlibrary.util.io

import android.content.Context

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/30 4:35 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
interface IKV {

    fun init(context: Context?)

    fun put(
        context: Context,
        key: String?,
        `object`: Any
    )

    fun <T> get(context: Context, key: String?, defaultObject: T): T?

    fun remove(context: Context, key: String?)
}