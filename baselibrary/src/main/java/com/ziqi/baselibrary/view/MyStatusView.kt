package com.ziqi.baselibrary.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.ziqi.baselibrary.R

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/7 4:16 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class MyStatusView : FrameLayout {
    private var mRootView: View? = null
    private var mIvLogo: ImageView? = null
    private var mTvMsg: TextView? = null
    private var mLlStatus: LinearLayout? = null
    private var mRvContent: RecyclerView? = null
    private var mProgressbar: ContentLoadingProgressBar? = null
    private var mFlLoading: FrameLayout? = null

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = -1
    ) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.view_base_content, this, true)
        mRootView?.apply {
            mIvLogo = findViewById(R.id.ivLogo)
            mTvMsg = findViewById(R.id.tvMsg)
            mLlStatus = findViewById(R.id.llStatus)
            mRvContent = findViewById(R.id.rvContent)
            mProgressbar = findViewById(R.id.progressbar)
            mFlLoading = findViewById(R.id.flLoading)
        }
        addView(mRootView)
    }
}