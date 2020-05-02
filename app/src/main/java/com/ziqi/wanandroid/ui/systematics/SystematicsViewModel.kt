package com.ziqi.wanandroid.ui.systematics

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.livedata.Event
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.wanandroid.bean.Article
import com.ziqi.wanandroid.bean.Tree
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
class SystematicsViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = SystematicsViewModel::class.java.simpleName

    var mTree: MutableLiveData<MutableList<Tree>> = MutableLiveData()

    var mArticleList: MutableLiveData<WanList<Article>> = MutableLiveData()

    var mContentStatusView: MutableLiveData<Event<Int>> = MutableLiveData()

    fun loadTree() = asyncExt(
        {
            mTree.value = async { NetRepository.tree().preProcessData() }.await()
            zContentView()
        },
        {
            zErrorView()
            zToast(errorInfo(it))
        }
    )

    fun loadArticleList(pos: Int, cid: Int) = asyncExt(
        {
            mArticleList.value =
                withContext(Dispatchers.IO) {
                    NetRepository.articleList(
                        pos,
                        cid
                    ).preProcessData()
                }
            if (pos == 0) {
                zRefresh(true)
                mContentStatusView.value = Event(1)
            } else {
                zLoadMore(true)
            }
        },
        {
            LogUtil.e(TAG, "loadArticleList.Error..", it)
            zToast(errorInfo(it))
            if (pos == 0) {
                zRefresh(false)
                mContentStatusView.value = Event(2)
            } else {
                zLoadMore(false)
            }
        })
}