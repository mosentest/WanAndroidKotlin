package com.ziqi.wanandroid.view

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/16 11:34 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class SwipeRefreshLayoutX : SwipeRefreshLayout {

    constructor(context: Context) : this(context, null)

    constructor(
        context: Context,
        attrs: AttributeSet? = null
    ) : super(context, attrs) {
        init()
    }

    fun init() {

    }
}