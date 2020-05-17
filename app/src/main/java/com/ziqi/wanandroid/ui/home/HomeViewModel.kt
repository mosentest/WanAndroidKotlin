package com.ziqi.wanandroid.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.wanandroid.commonlibrary.bean.Banner
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseViewModel
import kotlinx.coroutines.async

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/15 4:26 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class HomeViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = HomeViewModel::class.java.simpleName

    private val _mBanner: MutableLiveData<MutableList<Banner>> = MutableLiveData()
    val mBanner: LiveData<MutableList<Banner>>
        get() = _mBanner

    fun loadBanner() = asyncExt({
        _mBanner.value = async { NetRepository.banner().preProcessData() }.await()
        zContentView()
    }, {
        LogUtil.e(TAG, "loadBanner.Error..", it)
        zErrorView()
        zToast(errorInfo(it))
    })
}