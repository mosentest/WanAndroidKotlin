package com.ziqi.wanandroid.ui.recentblog

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.wanandroid.commonlibrary.bean.Article
import com.ziqi.wanandroid.commonlibrary.bean.WanList
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class RecentBlogViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = RecentBlogViewModel::class.java.simpleName

    private val _mArticleTop: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val mArticleTop: LiveData<MutableList<Article>>
        get() = _mArticleTop

    private val _mArticleList: MutableLiveData<WanList<Article>> = MutableLiveData()
    val mArticleList: LiveData<WanList<Article>>
        get() = _mArticleList

    fun loadArticleTop(showLoading: Boolean) = asyncExt(
        {
            _mArticleTop.value = async { NetRepository.articleTop().preProcessData() }.await()
            zContentView()
            loadOther()
            zRefresh(true)
        },
        {
            LogUtil.e(TAG, "loadArticleTop.Error..", it)
            if (_mArticleTop.value == null) {
                //如果这个数据为空才展示
                zErrorView()
            }
            zRefresh(false)
            zToast(errorInfo(it))
        }, showLoading
    )


    private fun loadOther() {
        loadArticleList(0)
    }

    fun loadArticleList(pos: Int) = asyncExt(
        {
            _mArticleList.value =
                withContext(Dispatchers.IO) { NetRepository.articleList(pos).preProcessData() }
            zLoadMore(true)
        },
        {
            LogUtil.e(TAG, "loadArticleList.Error..", it)
            zToast(errorInfo(it))
            zLoadMore(false)
        })


}
