package com.ziqi.wanandroid.ui.projectlist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.wanandroid.commonlibrary.bean.ListProject
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
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
class ProjectListViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = ProjectListViewModel::class.java.simpleName

    var mListProject: MutableLiveData<ListProject> = MutableLiveData()

    fun loadListProject(pos: Int, cid: Int) = asyncExt({
        mListProject.value = async { NetRepository.project(pos, cid).preProcessData() }.await()
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