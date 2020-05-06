package com.ziqi.wanandroid.commonlibrary.util.route

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Parcelable

import androidx.fragment.app.Fragment
import com.ziqi.baselibrary.util.LogUtil

import com.ziqi.baselibrary.util.StartActivityCompat
import com.ziqi.baselibrary.util.StringUtil
import com.ziqi.wanandroid.commonlibrary.ui.common.CommonActivity
import java.lang.reflect.Method

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2020/5/6-11:40
 * desc   :
 * version: 1.0
 */
object ParsePage {

    val TAG = ParsePage::class.java.simpleName

    fun method(
        method: String,
        titleName: String,
        showBack: Boolean,
        context: Context,
        fragment: Fragment?,
        requestCode: Int,
        info: Parcelable? = null
    ) {
        if (StringUtil.isEmpty(method)) {
            return
        }
        val declaredMethods = IStartPage::class.java.declaredMethods
        var activityName = ""
        var fragmentName = ""
        for (declaredMethod in declaredMethods) {
            LogUtil.i(TAG, "declaredMethod.name>>>" + declaredMethod.name)
            if (method == declaredMethod.name) {
                val annotations = declaredMethod.annotations
                for (annotation in annotations) {
                    if (annotation is StartFragment) {
                        fragmentName = annotation.value
                    }
                    if (annotation is StartActivity) {
                        activityName = annotation.value
                    }
                }
                break
            }
        }
        StartActivityCompat.startActivity(
            context,
            fragment,
            fragmentName,
            requestCode,
            Intent().apply {
                setComponent(
                    ComponentName(
                        context.packageName,
                        StringUtil.emptyTip(
                            activityName,
                            CommonActivity::class.java.name
                        )
                    )
                )
            },
            titleName,
            showBack,
            info
        )
    }
}
