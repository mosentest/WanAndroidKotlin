package com.ziqi.baselibrary.view.webview

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-08-15 21:56
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
object OpenWeb {
    @JvmStatic
    fun openUrl(context: Context, url: String?) {
        try {
            val localIntent = Intent(Intent.ACTION_VIEW)
            localIntent.addCategory("android.intent.category.BROWSABLE")
            localIntent.addCategory("android.intent.category.DEFAULT")
            val uri1 = Uri.parse("http://")
            val uri2 = Uri.parse("https://")
            localIntent.setDataAndType(uri1, null)
            localIntent.setDataAndType(uri2, null)
            val localList = context.packageManager.queryIntentActivities(localIntent, 1)
            if (localList != null && localList.size > 0) {
                localIntent.data = Uri.parse(url)
                localIntent.setClassName(
                    localList[0].activityInfo.packageName,
                    localList[0].activityInfo.name
                )
                localIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(localIntent)
            } else {
                val uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        } catch (e: Exception) {
        }
    }
}