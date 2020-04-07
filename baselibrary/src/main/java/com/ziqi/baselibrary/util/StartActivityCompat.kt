package com.ziqi.baselibrary.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import androidx.fragment.app.Fragment

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2019/5/27-16:41
 * desc   : 兼容所有情况的跳转模式
 * version: 1.0
 */
object StartActivityCompat {

    //public static final String NEXT_EXTRA = "next_extra";
    /**
     * 封装其他，其他参数都转成parcelable对象，方便代码跟踪
     * 也是为了saveInstance方便处理数据问题
     */
    const val NEXT_PARCELABLE = "next_parcelable"
    /**
     * 下个页面的标题
     */
    const val NEXT_TITLE = "next_title"
    /**
     * 控制下个页面是否显示标题
     */
    const val NEXT_SHOW_BACK = "next_show_back"

    /**
     * 基本跳转，带标题和返回处理
     *
     * @param context
     * @param fragment
     * @param title
     * @param showBack
     * @param intent
     */
    @JvmOverloads
    fun startActivity(
        context: Context,
        fragment: Fragment?,
        requestCode: Int = -1,
        intent: Intent,
        title: String?,
        showBack: Boolean,
        parcelable: Parcelable? = null
    ) {
        val bundle = Bundle()
        //设置相关参数
        if (parcelable != null) {
            bundle.putParcelable(NEXT_PARCELABLE, parcelable)
        }
        //设置相关参数
        if (!TextUtils.isEmpty(title)) {
            bundle.putString(NEXT_TITLE, title)
        }
        //设置相关参数
        bundle.putBoolean(NEXT_SHOW_BACK, showBack)
        intent.putExtras(bundle)
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode)
        } else {
            if (context is Activity) {
                context.startActivityForResult(intent, requestCode)
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
    }
}