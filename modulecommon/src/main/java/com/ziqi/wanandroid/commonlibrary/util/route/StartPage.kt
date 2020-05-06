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
import com.ziqi.wanandroid.commonlibrary.ui.imagepreview.ImagePreviewViewModel
import java.lang.reflect.Method

/**
 *    作者 : moziqi
 *    邮箱 : 709847739@qq.com
 *    时间   : 2020/5/6-11:20
 *    desc   :
 *    version: 1.0
 */
object StartPage {

    private val TAG: String = StartPage::class.java.simpleName


    fun toMain(context: Context) {
        context.startActivity(Intent().apply {
            component = ComponentName(
                context.packageName, "com.ziqi.wanandroid.ui.main.MainActivity"
            )
            //实现清除栈低的页面
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK)
        })
    }

    fun toWxArticle(
        context: Context,
        fragment: Fragment?,
        requestCode: Int = -1,
        info: Parcelable?
    ) {
        method(
            "toWxArticle",
            "公众号",
            true,
            context,
            fragment,
            requestCode,
            info
        )
    }

    fun toCollect(
        context: Context,
        fragment: Fragment?,
        requestCode: Int = -1,
        info: Parcelable?
    ) {
        method(
            "toCollect",
            "收藏",
            true,
            context,
            fragment,
            requestCode,
            info
        )
    }


    /**
     * 缓存反射的对象
     */
    private var mDeclaredMethods: Array<Method>? = null

    /**
     * 缓存列表
     */
    private var cacheMap = LinkedHashMap<String, ActInfo>(10)

    /**
     * 缓存下页面的信息
     */
    private class ActInfo {
        var activityName = ""
        var fragmentName = ""
    }


    private fun method(
        method: String,
        titleName: String,
        showBack: Boolean = false,
        context: Context,
        fragment: Fragment?,
        requestCode: Int,
        info: Parcelable? = null
    ) {
        if (StringUtil.isEmpty(method)) {
            return
        }
        var activityName = ""
        var fragmentName = ""
        val actInfo = cacheMap.get(method)
        if (actInfo != null) {
            activityName = actInfo.activityName
            fragmentName = actInfo.fragmentName
        } else {
            if (mDeclaredMethods == null) {
                mDeclaredMethods = IStartPage::class.java.declaredMethods
            }
            mDeclaredMethods?.let {
                for (declaredMethod in it) {
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
                            val actInfo = ActInfo().apply {
                                this.fragmentName = fragmentName
                                this.activityName = activityName
                            }
                            cacheMap.put(method, actInfo)
                        }
                        break
                    }
                }
            }
        }
        //启动对应页面
        if (fragmentName == "") {
            return
        }
        StartActivityCompat.startActivity(
            context,
            fragment,
            fragmentName,
            requestCode,
            Intent().apply {
                component = ComponentName(
                    context.packageName,
                    StringUtil.emptyTip(
                        activityName,
                        CommonActivity::class.java.name
                    )
                )
            },
            titleName,
            showBack,
            info
        )
    }
}