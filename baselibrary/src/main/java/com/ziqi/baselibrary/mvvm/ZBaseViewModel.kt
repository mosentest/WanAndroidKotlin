package com.ziqi.baselibrary.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

    private val _mStatusView: MutableLiveData<Event<Int>> = MutableLiveData()
    val mStatusView: LiveData<Event<Int>>
        get() = _mStatusView

    private val _mLoading: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val mLoading: LiveData<Event<Boolean>>
        get() = _mLoading

    private val _mToast: MutableLiveData<Event<String>> = MutableLiveData()
    val mToast: LiveData<Event<String>>
        get() = _mToast

    private val _mConfirmDialog: MutableLiveData<Event<String>> = MutableLiveData()
    val mConfirmDialog: LiveData<Event<String>>
        get() = _mConfirmDialog

    private val _mRefresh: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val mRefresh: LiveData<Event<Boolean>>
        get() = _mRefresh

    private val _mLoadMore: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val mLoadMore: LiveData<Event<Boolean>>
        get() = _mLoadMore

    /**
     * 展示loading
     */
    fun zShowLoadingDialog() {
        _mLoading.value = Event(true)
    }

    /**
     * 隐藏loading
     */
    fun zHideLoadingDialog() {
        _mLoading.value = Event(false)
    }

    /**
     * 内容布局
     */
    fun zContentView() {
        _mStatusView.value = Event(1)
    }

    /**
     * 错误布局
     */
    fun zErrorView() {
        _mStatusView.value = Event(2)
    }

    /**
     * toast提示
     */
    fun zToast(msg: String?) {
        _mToast.value = Event(msg ?: "")
    }

    fun zRefresh(success: Boolean) {
        _mRefresh.value = Event(success)
    }

    fun zLoadMore(success: Boolean) {
        _mLoadMore.value = Event(success)
    }

    /**
     * 对话框提示
     */
    fun zConfirmDialog(msg: String?) {
        _mConfirmDialog.value = Event(msg ?: "")
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