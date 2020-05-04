package com.ziqi.wanandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.ziqi.wanandroid.R

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

    private var textView: TextView

    var mRetryListener: RetryListener? = null

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
        //====
        addView(imageView)
        addView(textView)
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

    interface RetryListener {
        public fun onRetry()
    }
}