package com.ziqi.baselibrary.mvvm

import android.os.Bundle
import android.os.Parcelable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ziqi.baselibrary.base.ZBaseActivity
import java.lang.reflect.ParameterizedType

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/3 5:54 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
abstract class ViewModelActivity<VM : BaseViewModel, StartParams : Parcelable, Binding : ViewDataBinding> :
    ZBaseActivity<StartParams, Binding>() {

    protected var mViewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        createViewModel()
        super.onCreate(savedInstanceState)
    }

    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            mViewModel = ViewModelProvider(this).get(tClass) as VM
        }
        mViewModel?.mStatusView?.observe(this, Observer {
            when (it.getContentIfNotHandled()) {
                1 -> {
                    zStatusContentView()
                }
                2 -> {
                    zStatusErrorView()
                }
            }
        })
        mViewModel?.mLoading?.observe(this, Observer {
            if (it.getContentIfNotHandled() == true) {
                zShowLoadDialog(-1, null)
            } else {
                zHideLoadDialog(-1)
            }
        })
        mViewModel?.mToast?.observe(this, Observer {
            zToastShort(-1, it.getContentIfNotHandled())
        })
        mViewModel?.mConfirmDialog?.observe(this, Observer {
            zConfirmDialog(-1, it.getContentIfNotHandled())
        })
    }
}