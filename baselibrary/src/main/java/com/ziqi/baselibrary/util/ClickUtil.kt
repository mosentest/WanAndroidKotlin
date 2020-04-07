package com.ziqi.baselibrary.util

import android.os.SystemClock

/**
 * 判断屏幕两次有效点击事件
 */
object ClickUtil {
    /**
     * 双击拦截的时间间隔
     */
    private const val DOUBLE_CLICK_LIMIT_TIME = 300L
    /**
     * 最后一次点击的时间
     */
    private var mLastClickTime: Long = 0

    val isFastDoubleClick: Boolean
        get() {
            val clickSpace = SystemClock.elapsedRealtime() - mLastClickTime
            mLastClickTime = SystemClock.elapsedRealtime()
            return clickSpace <= DOUBLE_CLICK_LIMIT_TIME
        }
}