package com.ziqi.wanandroid.ui.recentproject

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ziqi.wanandroid.commonlibrary.bean.ListProject
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseViewModel
import com.ziqi.wanandroid.ui.recentblog.RecentBlogViewModel
import kotlinx.coroutines.async

class RecentProjectViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = RecentBlogViewModel::class.java.simpleName

    var mListProject: MutableLiveData<ListProject> = MutableLiveData()

    fun loadListProject(pos: Int) = asyncExt({
        mListProject.value = async { NetRepository.listproject(pos).preProcessData() }.await()
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
