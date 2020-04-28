package com.ziqi.wanandroid.ui.systematics

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.wanandroid.bean.*
import com.ziqi.wanandroid.net.NetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/15 4:26 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class SystematicsViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val TAG: String = SystematicsViewModel::class.java.simpleName

    var mTree: MutableLiveData<MutableList<Tree>> = MutableLiveData()

    fun loadTree() = asyncExt(
        {
            mTree.value = async { NetRepository.tree().preProcessData() }.await()
            zContentView()
        },
        {
            zErrorView()
            zToast("""${it.errMsg}[${it.code}]""")
        }
    )
}