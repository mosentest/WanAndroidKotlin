package com.ziqi.wanandroid.commonlibrary.util.imageload

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.ziqi.wanandroid.commonlibrary.view.ImageViewX

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/30 2:35 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
interface IImageLoad {

    fun loadUrl(context: Context, url: String?, imageView: ImageView?)

    fun loadUrl(fragment: Fragment, url: String?, imageView: ImageView?)

    fun loadUrl(
        fragment: Fragment,
        url: String?,
        imageViewX: ImageViewX?, @DrawableRes resourceId: Int,
        scaleType: ImageView.ScaleType
    )

    fun loadUrl2Circle(fragment: Fragment, url: String?, imageView: ImageView?)
}