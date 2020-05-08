package com.ziqi.wanandroid.ui.project

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ziqi.wanandroid.commonlibrary.bean.Tree
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.UserViewModel
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
class ProjectViewModel(ctx: Application) : UserViewModel(ctx) {

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