package com.ziqi.baselibrary.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ziqi.baselibrary.http.error.ExceptionHandle
import com.ziqi.baselibrary.http.error.ResponseThrowable
import com.ziqi.baselibrary.livedata.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/25 8:47 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
open class ZBaseViewModel(ctx: Application) : AndroidViewModel(ctx) {

    var mStatusView: MutableLiveData<Event<Int>> = MutableLiveData()

    var mLoading: MutableLiveData<Event<Boolean>> = MutableLiveData()

    var mToast: MutableLiveData<Event<String>> = MutableLiveData()

    var mConfirmDialog: MutableLiveData<Event<String>> = MutableLiveData()

    var mRefresh: MutableLiveData<Event<Boolean>> = MutableLiveData()

    var mLoadMore: MutableLiveData<Event<Boolean>> = MutableLiveData()

    /**
     * 展示loading
     */
    fun zShowLoadingDialog() {
        mLoading.value = Event(true)
    }

    /**
     * 隐藏loading
     */
    fun zHideLoadingDialog() {
        mLoading.value = Event(false)
    }

    /**
     * 内容布局
     */
    fun zContentView() {
        mStatusView.value = Event(1)
    }

    /**
     * 错误布局
     */
    fun zErrorView() {
        mStatusView.value = Event(2)
    }

    /**
     * toast提示
     */
    fun zToast(msg: String?) {
        mToast.value = Event(msg ?: "")
    }

    fun zRefresh(success: Boolean) {
        mRefresh.value = Event(success)
    }

    fun zLoadMore(success: Boolean) {
        mLoadMore.value = Event(success)
    }

    /**
     * 对话框提示
     */
    fun zConfirmDialog(msg: String?) {
        mConfirmDialog.value = Event(msg ?: "")
    }

    fun asyncExt(
        block: suspend CoroutineScope.() -> Unit,
        onError: (rt: ResponseThrowable) -> Unit = {},
        isLoading: Boolean = false
    ) {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            onError(ExceptionHandle.handleException(e))
        }) {
            try {
                if (isLoading) {
                    zShowLoadingDialog()
                }
                block.invoke(this)
            } finally {
                if (isLoading) {
                    zHideLoadingDialog()
                }
            }
        }
    }
}