package com.ziqi.wanandroid.commonlibrary.ui.start

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.consumer.ActivitySlidingBackConsumer
import com.ziqi.baselibrary.util.MyHandler
import com.ziqi.baselibrary.util.statusbar.StatusBarUtil
import com.ziqi.wanandroid.commonlibrary.ui.common.CommonActivity
import com.ziqi.wanandroid.commonlibrary.util.route.StartPage

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/10 3:17 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class StartActivity : CommonActivity<Parcelable>() {

    private var myHandler: MyHandler? = null

    override fun createFragment(): Fragment? {
//        return super.createFragment()
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTranslucentStatus(this)
        StatusBarUtil.setStatusBarDarkTheme(this, true)
        myHandler = MyHandler(lifecycle)
        myHandler?.postDelayed({
            StartPage.toMain(this)
            finish()
        }, 2000)
    }


    override fun zEnableSwipe(): Boolean {
        //activity侧滑返回
        SmartSwipe.wrap(this)
            .addConsumer(ActivitySlidingBackConsumer(this))
            .setRelativeMoveFactor(0.5F)
            .enableAllDirections()
        return false
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}