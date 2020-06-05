package com.ziqi.wanandroid.commonlibrary.ui.common

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.SmartSwipeWrapper
import com.billy.android.swipe.SwipeConsumer
import com.billy.android.swipe.consumer.ActivitySlidingBackConsumer
import com.billy.android.swipe.listener.SimpleSwipeListener
import com.billy.android.swipe.listener.SwipeListener
import com.ziqi.baselibrary.base.ZBaseActivity
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
open class CommonActivity<T : Parcelable> : ZBaseActivity<T, ViewDataBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * https://juejin.im/post/5d3fdc3af265da03c02bdbde
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (zEnableSwipe()) {
                //activity侧滑返回
                SmartSwipe.wrap(this)
                    .addConsumer(ActivitySlidingBackConsumer(this))
                    //设置联动系数
                    .setRelativeMoveFactor(0.5F)
                    //指定可侧滑返回的方向，如：enableLeft() 仅左侧可侧滑返回
                    .enableLeft()
            }
        }
    }

    open fun zEnableSwipe(): Boolean {
        return true
    }

    override fun createFragment(): Fragment? {
        var newFragment: Fragment? = null
        try {
            intent?.extras?.apply {
                val fragmentName = getString(StartActivityCompat.NEXT_FRAGMENT)
                if (!TextUtils.isEmpty(fragmentName)) {
                    val forName = Class.forName(fragmentName!!)
                    val newInstanceMethod =
                        forName.getDeclaredMethod("newInstance", Bundle::class.java)
                    newInstanceMethod.isAccessible = true
                    newFragment = newInstanceMethod.invoke(null, this) as Fragment
                }
            }
        } catch (e: Exception) {
            zToastShort(-1, "无法打开页面")
            finish()
        } finally {
            if (newFragment == null) {
                finish()
            }
        }
        return newFragment
    }

    override fun onClick(v: View?) {

    }

    override fun zVisibleToUser(isNewIntent: Boolean) {
    }
}