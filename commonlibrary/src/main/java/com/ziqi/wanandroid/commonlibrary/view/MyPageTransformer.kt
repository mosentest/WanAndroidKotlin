package com.ziqi.wanandroid.commonlibrary.view

import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/16 4:02 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class MyPageTransformer : ViewPager2.PageTransformer, ViewPager.PageTransformer {

    val MIN_ALPHA = 0.5f

    override fun transformPage(page: View, position: Float) {
        //LogUtil.i(zGetClassName(), "setPageTransformer:$position")
        if (position < -1 || position > 1) {
            page.scaleX = MIN_ALPHA
            page.scaleY = MIN_ALPHA
            page.alpha = MIN_ALPHA
        } else {
            if (position < 0) {
                //[0,-1]
                page.scaleX = MIN_ALPHA + (1f + position) * (1f - MIN_ALPHA)
                page.scaleY = MIN_ALPHA + (1f + position) * (1f - MIN_ALPHA)
                page.alpha = MIN_ALPHA + (1f + position) * (1f - MIN_ALPHA)
            } else {
                //[1,0]
                page.scaleX = MIN_ALPHA + (1f - position) * (1f - MIN_ALPHA)
                page.scaleY = MIN_ALPHA + (1f - position) * (1f - MIN_ALPHA)
                page.alpha = MIN_ALPHA + (1f - position) * (1f - MIN_ALPHA)
            }
        }
    }
}