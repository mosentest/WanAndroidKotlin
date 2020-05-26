package com.ziqi.wanandroid.ui.recentproject

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ziqi.wanandroid.commonlibrary.bean.ListProject
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseViewModel
import com.ziqi.wanandroid.ui.recentblog.RecentBlogViewModel
import kotlinx.coroutines.async

class RecentProjectViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = RecentBlogViewModel::class.java.simpleName

    private val _mListProject: MutableLiveData<ListProject> = MutableLiveData()
    val mListProject: LiveData<ListProject>
        get() = _mListProject

    fun loadListProject(pos: Int) = asyncExt({
        _mListProject.value = async { NetRepository.listproject(pos).preProcessData() }.await()
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
