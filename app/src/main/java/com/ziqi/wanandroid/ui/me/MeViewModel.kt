package com.ziqi.wanandroid.ui.me

import android.annotation.SuppressLint
import android.app.Application
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseViewModel
import com.ziqi.wanandroid.commonlibrary.util.LoginManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/3 6:54 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class MeViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = MeViewModel::class.java.simpleName

    private val _mLogout: MutableLiveData<Any> = MutableLiveData()
    val mLogout: LiveData<Any>
        get() = _mLogout


    fun logout() {
        asyncExt({
            _mLogout.value =
                withContext(Dispatchers.IO) {
                    NetRepository.logout().preProcessData()
                    LoginManager.logout()
                }
        }, {
            zToast(errorInfo(it))
        }, true)
    }

    @SuppressLint("RestrictedApi")
    fun parallelDialog() {
        for (i in 1..3) {
            ArchTaskExecutor.getIOThreadExecutor().execute {
                try {
                    LogUtil.i(TAG, Thread.currentThread().name + ".start")
                    ArchTaskExecutor.getMainThreadExecutor().execute {
                        zShowLoadingDialog()
                    }
                    Thread.sleep(Random().nextInt(5) * 1000L)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    LogUtil.i(TAG, Thread.currentThread().name + ".end")
                    ArchTaskExecutor.getMainThreadExecutor().execute {
                        zHideLoadingDialog()
                    }
                }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    fun serialDialog() {
        for (i in 1..5) {
            asyncExt({
                withContext(Dispatchers.IO) { sss() }
            }, {

            }, true)
        }
    }

    private fun sss() {
        try {
            Thread.sleep(Random().nextInt(3) * 1000L)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}