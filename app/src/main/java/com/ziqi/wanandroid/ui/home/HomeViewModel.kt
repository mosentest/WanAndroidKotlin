package com.ziqi.wanandroid.ui.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.wanandroid.bean.Article
import com.ziqi.wanandroid.bean.Banner
import com.ziqi.wanandroid.bean.WanList
import com.ziqi.wanandroid.net.NetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

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

    var mArticleList: MutableLiveData<WanList<Article>> = MutableLiveData()

    private fun loadBanner() = asyncExt({
        mBanner.value = async { NetRepository.banner().preProcessData() }.await()
    })

    fun loadArticleTop(showLoading: Boolean) = asyncExt(
        {
            mArticleTop.value = async { NetRepository.articleTop().preProcessData() }.await()
            zContentView()
            loadOther()
            zRefresh(true)
        },
        {
            LogUtil.e(TAG, "loadArticleTop.Error..", it)
            zErrorView()
            zRefresh(false)
            zToast("""${it.errMsg}[${it.code}]""")
        }, showLoading
    )


    private fun loadOther() {
        loadBanner()
        loadArticleList(0)
    }

    fun loadArticleList(pos: Int) = asyncExt(
        {
            mArticleList.value =
                withContext(Dispatchers.IO) {
                    NetRepository.articleList(
                        pos
                    ).preProcessData()
                }
            zLoadMore(true)
        },
        {
            LogUtil.e(TAG, "loadArticleList.Error..", it)
            zToast("""${it.errMsg}[${it.code}]""")
            zLoadMore(false)
        })
}