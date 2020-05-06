package com.ziqi.wanandroid.commonlibrary.util.route

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.fragment.app.Fragment

/**
 *    作者 : moziqi
 *    邮箱 : 709847739@qq.com
 *    时间   : 2020/5/6-11:20
 *    desc   :
 *    version: 1.0
 */
object StartPage {
    fun toMain(context: Context) {
        context.startActivity(Intent().apply {
            setComponent(
                ComponentName(
                    context.packageName, "com.ziqi.wanandroid.ui.main.MainActivity"
                )
            )
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        })
    }

    fun toWxArticle(
        context: Context,
        fragment: Fragment?,
        requestCode: Int = -1,
        info: Parcelable?
    ) {
        ParsePage.method(
            "toWxArticle",
            "公众号",
            true,
            context,
            fragment,
            requestCode,
            info
        )
    }
}