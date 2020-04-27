package com.ziqi.baselibrary.util

import android.app.Activity
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import com.ziqi.baselibrary.R


/**
 *    作者 : moziqi
 *    邮箱 : 709847739@qq.com
 *    时间   : 2020/4/27-12:00
 *    desc   :
 *    version: 1.0
 */
object ShareUtil {

    fun shareText(activity: Activity, title: String, content: String) {
        if (StringUtil.isEmpty(content)) {
            return
        }
        var shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, content)
        //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
        shareIntent = Intent.createChooser(shareIntent, title)
        activity.startActivity(shareIntent)
    }
}