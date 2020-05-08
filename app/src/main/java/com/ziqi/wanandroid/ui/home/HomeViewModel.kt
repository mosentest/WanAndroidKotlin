package com.ziqi.wanandroid.ui.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.wanandroid.commonlibrary.bean.Banner
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.UserViewModel
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
class HomeViewModel(ctx: Application) : UserViewModel(ctx) {

    private val TAG: String = HomeViewModel::class.java.simpleName

    var mBanner: MutableLiveData<MutableList<Banner>> = MutableLiveData()

    public fun loadBanner() = asyncExt({
        mBanner.value = async { NetRepository.banner().preProcessData() }.await()
        zContentView()
    }, {
        LogUtil.e(TAG, "loadBanner.Error..", it)
        zErrorView()
        zToast(errorInfo(it))
    })
}