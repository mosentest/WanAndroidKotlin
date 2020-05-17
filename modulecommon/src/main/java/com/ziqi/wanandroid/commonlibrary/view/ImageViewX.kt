package com.ziqi.wanandroid.commonlibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.ziqi.wanandroid.commonlibrary.R

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/4 7:27 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class ImageViewX : FrameLayout {


    private var imageView: ImageView
    private var dstOutView: DstOutView

    private var textView: TextView

    var mRetryListener: RetryListener? = null
        set(value) {
            field = null
            field = value
        }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributes: AttributeSet?) : super(context, attributes) {
        imageView = ImageView(context)
        imageView.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        //====
        textView = TextView(context)
        textView.gravity = Gravity.CENTER
        textView.visibility = View.GONE
        textView.setTextColor(resources.getColor(R.color.color_FFFFFF))
        textView.textSize = 16.0F
        //===
        dstOutView = DstOutView(context)
        dstOutView.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        //====
        addView(imageView)
        addView(textView)
        addView(dstOutView)
        //增加回调
        textView.setOnClickListener {
            mRetryListener?.onRetry()
        }
    }

    fun setRetryTip(tip: String): ImageViewX {
        textView.text = tip
        return this
    }

    fun showRetry(boolean: Boolean) {
        textView.visibility = if (boolean) View.VISIBLE else View.GONE
    }

    fun getTarget(): ImageView {
        return imageView
    }

    fun setProgress(progress: Int) {
        dstOutView.setProgress(progress)
        dstOutView.visibility = if (progress >= 100) View.GONE else View.VISIBLE
    }

    interface RetryListener {
        public fun onRetry()
    }
}