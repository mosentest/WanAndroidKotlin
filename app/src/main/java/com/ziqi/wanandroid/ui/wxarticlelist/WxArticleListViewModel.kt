package com.ziqi.wanandroid.ui.wxarticlelist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.wanandroid.commonlibrary.bean.ListProject
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import kotlinx.coroutines.async

class WxArticleListViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = WxArticleListViewModel::class.java.simpleName

    var mListProject: MutableLiveData<ListProject> = MutableLiveData()

    fun wxArticleList(pos: Int, cid: Int) = asyncExt({
        mListProject.value =
            async { NetRepository.wxArticleList(pos, cid).preProcessData() }.await()
        zContentView()
        if (pos == 0) {
            zRefresh(true)
        } else {
            zLoadMore(true)
        }
    }, {
        zErrorView()
        zToast(errorInfo(it))
        if (pos == 0) {
            zRefresh(false)
        } else {
            zLoadMore(false)
        }
    })
}
