package com.ziqi.wanandroid.ui.project

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.wanandroid.bean.Tree
import com.ziqi.wanandroid.net.NetRepository
import kotlinx.coroutines.async

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/2 8:01 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class ProjectViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = ProjectViewModel::class.java.simpleName

    var mTree: MutableLiveData<MutableList<Tree>> = MutableLiveData()

    fun loadTree() = asyncExt({
        mTree.value = async { NetRepository.projectTree().preProcessData() }.await()
        zContentView()
    }, {
        zErrorView()
        zToast(errorInfo(it))
    })
}