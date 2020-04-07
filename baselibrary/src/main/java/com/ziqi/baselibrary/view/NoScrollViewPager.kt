package com.ziqi.baselibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoScrollViewPager : ViewPager {

    private var noScroll = true

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
    }

    constructor(context: Context?) : super(context!!) {

    }

    fun setNoScroll(noScroll: Boolean) {
        this.noScroll = noScroll
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (noScroll) false else super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (noScroll) false else super.onInterceptTouchEvent(event)
    }
}