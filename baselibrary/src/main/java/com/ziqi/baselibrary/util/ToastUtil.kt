package com.ziqi.baselibrary.util

import android.text.TextUtils
import android.widget.Toast
import java.lang.Exception

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/6 12:37 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object ToastUtil {

    private var currentMsg: String? = null

    fun showLongToast(msg: String?) {
        try {
            if (TextUtils.isEmpty(msg)) {
                return
            }
            Toast.makeText(ContextUtils.context, msg, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            LogUtil.e("showLongToast", e)
        }

    }

    fun showShortToast(msg: String?) {
        try {
            if (TextUtils.isEmpty(msg)) {
                return
            }
            Toast.makeText(ContextUtils.context, msg, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            LogUtil.e("showShortToast", e)
        }
    }
}