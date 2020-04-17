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
class LogUtil {


    companion object {

        private val TAG: String = LogUtil.javaClass.simpleName

        @JvmStatic
        public fun i(msg: String) {
            Log.i(TAG, msg)
        }

        @JvmStatic
        public fun i(tag: String, msg: String) {
            Log.i(tag, msg)
        }

        @JvmStatic
        public fun e(tag: String, msg: String, throwable: Throwable) {
            Log.e(tag, msg, throwable)
        }

        @JvmStatic
        public fun e(msg: String, throwable: Throwable) {
            Log.e(TAG, msg, throwable)
        }
    }


}