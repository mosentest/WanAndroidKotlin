package com.ziqi.wanandroid.ui.recentblog

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.wanandroid.bean.Article
import com.ziqi.wanandroid.bean.WanList
import com.ziqi.wanandroid.net.NetRepository
import com.ziqi.wanandroid.ui.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class RecentBlogViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = RecentBlogViewModel::class.java.simpleName

    var mArticleTop: MutableLiveData<MutableList<Article>> = MutableLiveData()

    var mArticleList: MutableLiveData<WanList<Article>> = MutableLiveData()

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
            zToast(errorInfo(it))
        }, showLoading
    )


    private fun loadOther() {
        loadArticleList(0)
    }

    fun loadArticleList(pos: Int) = asyncExt(
        {
            mArticleList.value =
                withContext(Dispatchers.IO) { NetRepository.articleList(pos).preProcessData() }
            zLoadMore(true)
        },
        {
            LogUtil.e(TAG, "loadArticleList.Error..", it)
            zToast(errorInfo(it))
            zLoadMore(false)
        })


}
