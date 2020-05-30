package com.ziqi.wanandroid.me.ui.wxarticlelist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ziqi.wanandroid.commonlibrary.bean.ListProject
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseViewModel
import kotlinx.coroutines.async

class WxArticleListViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = WxArticleListViewModel::class.java.simpleName

    private val _mListProject: MutableLiveData<ListProject> = MutableLiveData()
    val mListProject: LiveData<ListProject>
        get() = _mListProject

    fun wxArticleList(pos: Int, cid: Int) = asyncExt({
        _mListProject.value =
            async { NetRepository.wxArticleList(pos, cid).preProcessData() }.await()
        zContentView()
        if (pos == 0) {
            zRefresh(true)
        } else {
            zLoadMore(true)
        }
    }, {
        zToast(errorInfo(it))
        if (pos == 0) {
            if (_mListProject.value == null) {
                zErrorView()
            }
            zRefresh(false)
        } else {
            zLoadMore(false)
        }
    })
}
