package com.ziqi.wanandroid.commonlibrary.ui.globaldialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.ziqi.baselibrary.common.WebFragment
import com.ziqi.baselibrary.util.StartActivityCompat
import com.ziqi.wanandroid.commonlibrary.ui.common.CommonActivity
import com.ziqi.wanandroid.commonlibrary.ui.common.WebActivity
import com.ziqi.wanandroid.commonlibrary.util.StartUtil

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/4 7:00 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class GlobalActivity : CommonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置1像素
//        val window = window
//        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH)
//        window.setGravity(Gravity.START or Gravity.TOP)
//        val params = window.attributes
//        params.x = 0
//        params.y = 0
//        params.height = 1
//        params.width = 1
//        window.attributes = params
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        //不需要这个
//        super.onBackPressed()
    }

    companion object {
        /**
         * 启动登录全局的对话框
         */
        fun start(context: Context, globalParams: GlobalParams) {
            StartActivityCompat.startActivity(
                context,
                null,
                GlobalFragment::class.java.name,
                -1,
                Intent(context, GlobalActivity::class.java),
                "",
                false,
                globalParams
            )
        }
    }
}