package com.ziqi.wanandroid.me.ui.wxarticle

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ziqi.wanandroid.commonlibrary.bean.Tree
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseViewModel
import kotlinx.coroutines.async

class WxArticleViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = WxArticleViewModel::class.java.simpleName

    private val _mTree: MutableLiveData<MutableList<Tree>> = MutableLiveData()
    val mTree: LiveData<MutableList<Tree>>
        get() = _mTree

    fun loadTree() = asyncExt({
        _mTree.value = async { NetRepository.wxArticleChapters().preProcessData() }.await()
        zContentView()
    }, {
        zErrorView()
        zToast(errorInfo(it))
    })

}
