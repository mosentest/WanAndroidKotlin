package com.ziqi.baselibrary.util

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/5 6:00 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class MyHandler : Handler, LifecycleObserver {

    var mLifecycle: Lifecycle? = null

    constructor(lifecycle: Lifecycle?) : super(Looper.getMainLooper()) {
        mLifecycle = lifecycle
        lifecycle?.addObserver(this)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        removeCallbacksAndMessages(null)
        mLifecycle?.removeObserver(this)
    }
}