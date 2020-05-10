package com.ziqi.wanandroid.commonlibrary.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ziqi.baselibrary.util.MyHandler
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
class StartActivity : CommonActivity() {

    private var myHandler: MyHandler? = null

    override fun createFragment(): Fragment? {
//        return super.createFragment()
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myHandler = MyHandler(lifecycle)
        myHandler?.postDelayed({
            StartPage.toMain(this)
            finish()
        }, 2000)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}