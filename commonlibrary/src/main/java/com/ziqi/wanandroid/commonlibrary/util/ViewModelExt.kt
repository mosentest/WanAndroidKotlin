package com.ziqi.wanandroid.commonlibrary.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/16 9:26 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
fun ViewModel.asyncExt(
    onStart: () -> Unit = {},
    block: suspend CoroutineScope.() -> Unit,
    onError: (e: Throwable) -> Unit = {},
    onComplete: () -> Unit = {}
) {
    viewModelScope.launch(CoroutineExceptionHandler { _, e ->
        onError(e)
    }) {
        try {
            onStart()
            block.invoke(this)
        } finally {
            onComplete()
        }
    }
}