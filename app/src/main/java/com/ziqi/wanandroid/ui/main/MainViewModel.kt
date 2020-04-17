package com.ziqi.wanandroid.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
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
class MainViewModel(ctx: Application) : AndroidViewModel(ctx) {

    private val TAG: String = MainViewModel::class.java.simpleName

    var mBanner: MutableLiveData<MutableList<Banner>> = MutableLiveData()

    var mArticleTop: MutableLiveData<MutableList<Article>> = MutableLiveData()

    var mHomeStatusView: MutableLiveData<Int> = MutableLiveData()
    var mToast: MutableLiveData<String> = MutableLiveData()

    fun loadBanner() = asyncExt({

    }, {
        val data = async { NetRepository.banner().preProcessData() }
        mBanner.value = data.await()
    })

    fun loadArticleTop(showLoading: Boolean) = asyncExt({
        if (showLoading) {
            //
        }
    }, {
        val data = async { NetRepository.articleTop().preProcessData() }
        mArticleTop.value = data.await()
        mHomeStatusView.value = 1
        loadBanner()
    }, {
        mHomeStatusView.value = 2
        mToast.value = it.message
        LogUtil.e(TAG, "loadArticleTop", it)
    }, {
        if (showLoading) {
            //
        }

    })
}