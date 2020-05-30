package com.ziqi.wanandroid.ui.systematics

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.livedata.Event
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.wanandroid.commonlibrary.bean.Article
import com.ziqi.wanandroid.commonlibrary.bean.Tree
import com.ziqi.wanandroid.commonlibrary.bean.WanList
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseViewModel
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
class SystematicsViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = SystematicsViewModel::class.java.simpleName

    private val _mTree: MutableLiveData<MutableList<Tree>> = MutableLiveData()
    val mTree: LiveData<MutableList<Tree>>
        get() = _mTree

    private val _mArticleList: MutableLiveData<WanList<Article>> = MutableLiveData()
    val mArticleList: LiveData<WanList<Article>>
        get() = _mArticleList

    private val _mContentStatusView: MutableLiveData<Event<Int>> = MutableLiveData()
    val mContentStatusView: LiveData<Event<Int>>
        get() = _mContentStatusView

    fun loadTree() = asyncExt(
        {
            _mTree.value = async { NetRepository.tree().preProcessData() }.await()
            zContentView()
        },
        {
            zErrorView()
            zToast(errorInfo(it))
        }
    )

    fun loadArticleList(showLoading: Boolean, pos: Int, cid: Int) = asyncExt(
        {
            _mArticleList.value =
                withContext(Dispatchers.IO) {
                    NetRepository.articleList(
                        pos,
                        cid
                    ).preProcessData()
                }
            if (pos == 0) {
                zRefresh(true)
                _mContentStatusView.value = Event(1)
            } else {
                zLoadMore(true)
            }
        },
        {
            LogUtil.e(TAG, "loadArticleList.Error..", it)
            zToast(errorInfo(it))
            if (pos == 0) {
                if (_mArticleList.value == null) {
                    _mContentStatusView.value = Event(2)
                }
                zRefresh(false)
            } else {
                zLoadMore(false)
            }
        },
        showLoading
    )
}