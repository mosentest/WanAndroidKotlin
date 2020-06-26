package com.ziqi.wanandroid.me.ui.collect

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ziqi.wanandroid.commonlibrary.bean.ListProject
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseViewModel
import kotlinx.coroutines.async

class CollectViewModel(ctx: Application) : BaseViewModel(ctx) {


    private val _mLists: MutableLiveData<ListProject> = MutableLiveData()
    val mLists: LiveData<ListProject>
        get() = _mLists

    fun getList(pos: Int) = asyncExt({
        _mLists.value =
            async { NetRepository.lgCollectList(pos).preProcessData() }.await()
        zContentView()
        if (pos == 0) {
            zRefresh(true)
        } else {
            zLoadMore(true)
        }
    }, {
        zToast(errorInfo(it))
        if (pos == 0) {
            if (_mLists.value == null) {
                zErrorView()
            }
            zRefresh(false)
        } else {
            zLoadMore(false)
        }
    })
}
