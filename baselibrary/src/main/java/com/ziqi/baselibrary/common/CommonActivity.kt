package com.ziqi.baselibrary.common

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ziqi.baselibrary.base.ZBaseActivity
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.baselibrary.util.StartActivityCompat

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/10 2:38 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class CommonActivity : ZBaseActivity<ViewDataBinding>() {
    override fun createFragment(): Fragment? {
        try {
            intent.extras?.apply {
                val fragmentName = getString(StartActivityCompat.NEXT_FRAGMENT)
                if (!TextUtils.isEmpty(fragmentName)) {
                    val forName = Class.forName(fragmentName!!)
                    val newInstanceMethod =
                        forName.getDeclaredMethod("newInstance", Bundle::class.java)
                    newInstanceMethod.isAccessible = true
                    val invokeFragment = newInstanceMethod.invoke(null, this) as Fragment
                    return invokeFragment
                }
            }
        } catch (e: Exception) {
            zToastShort(-1, "无法打开页面")
            finish()
        }
        return null
    }

    override fun onClick(v: View?) {

    }

    override fun zVisibleToUser(isNewIntent: Boolean) {
    }
}