package com.ziqi.wanandroid.util

import android.content.Context
import androidx.fragment.app.Fragment

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/3 6:56 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object LoginManager {

    fun isLogin(): Boolean {
        return false
    }

    fun isNoLogin(): Boolean {
        return !isLogin()
    }

    fun toLogin(context: Context, fragment: Fragment) {
        StartUtil.startLoginFragment(context, fragment, -1, null)
    }
}