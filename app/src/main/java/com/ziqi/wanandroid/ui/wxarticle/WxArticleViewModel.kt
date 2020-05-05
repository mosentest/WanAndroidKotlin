package com.ziqi.wanandroid.ui.wxarticle

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.wanandroid.bean.Tree
import com.ziqi.wanandroid.net.NetRepository
import com.ziqi.wanandroid.ui.common.UserViewModel
import com.ziqi.wanandroid.ui.project.ProjectViewModel
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
