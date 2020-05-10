package com.ziqi.baselibrary.util

import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.ziqi.baselibrary.R
import java.lang.Exception

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/6 12:37 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object ToastUtil {

    var mToast: Toast? = null

    var mCurrentMsg: String? = null

    fun showDefaultShortToast(msg: String?) {
        try {
            if (TextUtils.isEmpty(msg)) {
                return
            }
            Toast.makeText(ContextUtils.context, msg, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            LogUtil.e("showLongToast", e)
        }
    }

    fun showDefaultLongToast(msg: String?) {
        try {
            if (TextUtils.isEmpty(msg)) {
                return
            }
            Toast.makeText(ContextUtils.context, msg, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            LogUtil.e("showLongToast", e)
        }
    }

    fun showLongToast(msg: String?) {
        try {
            if (TextUtils.isEmpty(msg)) {
                return
            }
            if (mToast == null || mCurrentMsg != msg) {
                mToast = null
                mToast = Toast(ContextUtils.context)
                val rootView =
                    LayoutInflater.from(ContextUtils.context!!).inflate(R.layout.layout_toast, null)
                mToast?.view = rootView
                mToast?.duration = Toast.LENGTH_LONG
                mToast?.setGravity(Gravity.CENTER, 0, 0)
            }
            mToast?.view?.findViewById<TextView>(R.id.toast)?.text = msg
            mToast?.show()
            mCurrentMsg = msg
        } catch (e: Exception) {
            LogUtil.e("showLongToast", e)
        }
    }

    fun showShortToast(msg: String?) {
        try {
            if (TextUtils.isEmpty(msg)) {
                return
            }
            if (mToast == null || mCurrentMsg != msg) {
                mToast = null
                mToast = Toast(ContextUtils.context)
                val rootView =
                    LayoutInflater.from(ContextUtils.context!!).inflate(R.layout.layout_toast, null)
                mToast?.view = rootView
                mToast?.duration = Toast.LENGTH_SHORT
                mToast?.setGravity(Gravity.CENTER, 0, 0)
            }
            mToast?.view?.findViewById<TextView>(R.id.toast)?.text = msg
            mToast?.show()
            mCurrentMsg = msg
        } catch (e: Exception) {
            LogUtil.e("showShortToast", e)
        }
    }
}