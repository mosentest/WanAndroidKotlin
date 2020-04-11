package com.ziqi.baselibrary.util

import android.util.Log

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/6 11:49 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object LogUtil {

    @JvmStatic
    public fun i(msg: String) {
        Log.i("LogUtil", msg)
    }

    @JvmStatic
    public fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    @JvmStatic
    public fun e(tag: String, msg: String, throwable: Throwable) {
        Log.e(tag, msg, throwable)
    }
}