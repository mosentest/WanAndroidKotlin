package com.ziqi.wanandroid.commonlibrary.ui.globaldialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.ziqi.wanandroid.commonlibrary.ui.common.CommonActivity

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/4 7:00 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class LoginDialogActivity : CommonActivity() {

    var dialogFragment: LoginDialogFragment? = null

    /**
     * 不要
     * fragment
     * */
    override fun createFragment(): Fragment? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置1像素
        //设置1像素
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH)
        window.setGravity(Gravity.START or Gravity.TOP)
        val params = window.attributes
        params.x = 0
        params.y = 0
        params.height = 1
        params.width = 1
        window.attributes = params
        if (savedInstanceState == null) {
            val content = intent?.getStringExtra("content")
            dialogFragment =
                LoginDialogFragment.newInstance(
                    content
                )
            dialogFragment?.show(supportFragmentManager, content)
        }
        if (dialogFragment == null) {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dialogFragment?.dismissAllowingStateLoss()
        dialogFragment = null
    }

    override fun onBackPressed() {
        //不需要这个
//        super.onBackPressed()
    }

    companion object {
        /**
         * 启动登录全局的对话框
         */
        fun start(context: Context?, content: String) {
            context?.startActivity(Intent(context, LoginDialogActivity::class.java).apply {
                putExtra("content", content)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }
}