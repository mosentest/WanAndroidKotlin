package com.ziqi.baselibrary.mvvm

import android.os.Bundle
import android.os.Parcelable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ziqi.baselibrary.base.ZBaseFragment
import java.lang.reflect.ParameterizedType

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/25 8:58 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
abstract class ViewModelFragment<VM : BaseViewModel, StartParams : Parcelable, Binding : ViewDataBinding> :
    ZBaseFragment<StartParams, Binding>() {

    protected var mViewModel: VM? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel()
    }

    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            mViewModel = ViewModelProvider(this).get(tClass) as VM
        }
        mViewModel?.mStatusView?.observe(viewLifecycleOwner, Observer {
            it?.apply {
                when (getContentIfNotHandled()) {
                    1 -> {
                        zStatusContentView()
                    }
                    2 -> {
                        zStatusErrorView()
                    }
                }
            }
        })
        mViewModel?.mLoading?.observe(viewLifecycleOwner, Observer {
            it?.apply {
                if (getContentIfNotHandled() == true) {
                    zShowLoadDialog(-1, null)
                } else {
                    zHideLoadDialog(-1)
                }
            }
        })
        mViewModel?.mToast?.observe(viewLifecycleOwner, Observer {
            it?.apply {
                zToastShort(-1, getContentIfNotHandled())
            }
        })
        mViewModel?.mConfirmDialog?.observe(viewLifecycleOwner, Observer {
            it?.apply {
                zConfirmDialog(-1, getContentIfNotHandled())
            }
        })
    }
}