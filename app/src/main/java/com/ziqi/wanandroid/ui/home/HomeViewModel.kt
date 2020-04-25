package com.ziqi.wanandroid.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.ziqi.baselibrary.livedata.Event
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.baselibrary.util.SystemTool
import com.ziqi.wanandroid.BuildConfig
import com.ziqi.wanandroid.bean.Article
import com.ziqi.wanandroid.bean.Banner
import com.ziqi.wanandroid.bean.WanResponse
import com.ziqi.wanandroid.net.NetRepository
import com.ziqi.wanandroid.util.asyncExt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

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

    var mBanner: MutableLiveData<MutableList<Banner>> = MutableLiveData()

    var mArticleTop: MutableLiveData<MutableList<Article>> = MutableLiveData()

    private fun loadBanner() = asyncExt({}, {
        val data = async { NetRepository.banner().preProcessData() }
        mBanner.value = data.await()
    })

    fun loadArticleTop(showLoading: Boolean) = asyncExt({
        if (showLoading) {
            zShowLoadingDialog()
        }
    }, {
        val data = async { NetRepository.articleTop().preProcessData() }
        mArticleTop.value = data.await()
        loadBanner()
        zContentView()
    }, {
        zErrorView()
        zToast(it.message)
        LogUtil.e(TAG, "loadArticleTop.Error..", it)
    }, {
        if (showLoading) {
            zHideLoadingDialog()
        }
    })
}