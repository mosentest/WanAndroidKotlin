package com.ziqi.wanandroid.ui.recentproject

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.wanandroid.bean.ListProject
import com.ziqi.wanandroid.net.NetRepository
import com.ziqi.wanandroid.ui.recentblog.RecentBlogViewModel
import kotlinx.coroutines.async

class RecentProjectViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = RecentBlogViewModel::class.java.simpleName

    var mListProject: MutableLiveData<ListProject> = MutableLiveData()

    public fun loadListProject(pos: Int) = asyncExt({
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
