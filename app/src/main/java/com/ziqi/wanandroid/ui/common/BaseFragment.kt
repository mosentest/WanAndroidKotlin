package com.ziqi.wanandroid.ui.common

import android.os.Parcelable
import androidx.databinding.ViewDataBinding
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.baselibrary.mvvm.ViewModelFragment

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/1 1:53 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
abstract class BaseFragment<VM : BaseViewModel, StartParams : Parcelable, Binding : ViewDataBinding> :
    ViewModelFragment<VM, StartParams, Binding>(),
    SwipeRefreshLayout.OnRefreshListener {

    abstract fun initView()

    abstract fun dealViewModel()
}
