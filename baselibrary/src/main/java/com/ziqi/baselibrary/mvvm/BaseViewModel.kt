package com.ziqi.baselibrary.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.livedata.Event

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/25 8:47 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
open class BaseViewModel(ctx: Application) : AndroidViewModel(ctx) {

    var mStatusView: MutableLiveData<Event<Int>> = MutableLiveData()

    var mLoading: MutableLiveData<Event<Boolean>> = MutableLiveData()

    var mToast: MutableLiveData<Event<String>> = MutableLiveData()

    fun zShowLoadingDialog() {
        mLoading.value = Event(true)
    }

    fun zHideLoadingDialog() {
        mLoading.value = Event(true)
    }

    fun zContentView() {
        mStatusView.value = Event(1)
    }

    fun zErrorView() {
        mStatusView.value = Event(2)
    }

    fun zToast(msg: String?) {
        mToast.value = Event(msg ?: "")
    }
}