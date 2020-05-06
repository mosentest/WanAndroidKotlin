package com.ziqi.wanandroid.me.ui.wxarticle

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ziqi.wanandroid.commonlibrary.bean.Tree
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.UserViewModel
import kotlinx.coroutines.async

class WxArticleViewModel(ctx: Application) : UserViewModel(ctx) {

    private val TAG: String = WxArticleViewModel::class.java.simpleName

    var mTree: MutableLiveData<MutableList<Tree>> = MutableLiveData()

    fun loadTree() = asyncExt({
        mTree.value = async { NetRepository.wxArticleChapters().preProcessData() }.await()
        zContentView()
    }, {
        zErrorView()
        zToast(errorInfo(it))
    })

}
