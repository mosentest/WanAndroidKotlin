package com.ziqi.wanandroid.commonlibrary.ui.register

import android.os.Bundle
import android.os.Parcelable
import com.ziqi.baselibrary.util.statusbar.StatusBarUtil
import com.ziqi.wanandroid.commonlibrary.ui.common.CommonActivity

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/3 8:31 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class RegisterActivity : CommonActivity<Parcelable>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTranslucentStatus(this)
        StatusBarUtil.setStatusBarDarkTheme(this, false)
    }
}