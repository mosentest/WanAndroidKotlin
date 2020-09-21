package com.ziqi.wanandroid.commonlibrary

import android.app.Application
import com.didichuxing.doraemonkit.DoraemonKit
import com.ziqi.baselibrary.util.ContextUtils
import com.ziqi.baselibrary.util.io.KVUtils


/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/9 9:08 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextUtils.context = this
        KVUtils.instance.init(this)
//        DoraemonKit.install(this, getPackageName());
    }
}