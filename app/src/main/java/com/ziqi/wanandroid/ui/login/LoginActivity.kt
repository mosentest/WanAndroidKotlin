package com.ziqi.wanandroid.ui.login

import android.os.Bundle
import com.ziqi.baselibrary.util.statusbar.StatusBarUtil
import com.ziqi.wanandroid.ui.common.CommonActivity

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/3 8:31 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class LoginActivity : CommonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTranslucentStatus(this)
    }
}