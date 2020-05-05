package com.ziqi.wanandroid.bean

import com.ziqi.baselibrary.http.error.ResponseThrowable
import com.ziqi.baselibrary.util.ContextUtils
import com.ziqi.wanandroid.ui.globaldialog.LoginDialogActivity

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/15 11:06 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class WanResponse<T> {

    var errorCode = 0

    var errorMsg: String? = null

    var data: T? = null

    fun preProcessData(): T? {
        return if (errorCode == 0) data else throw ResponseThrowable(
            errorCode,
            errorMsg ?: ""
        ).apply {
            //errorCode = -1001 代表登录失效，需要重新登录。
            //errorCode = -1002 代表更新
            if (errorCode == -1001) {
                LoginDialogActivity.start(ContextUtils.context, errMsg)
            } else if (errorCode == -1002) {
                //弹出检查更新全局对话框
            }
        }
    }
}