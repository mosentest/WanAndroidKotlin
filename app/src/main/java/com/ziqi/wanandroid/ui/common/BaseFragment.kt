package com.ziqi.wanandroid.ui.common

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import androidx.databinding.ViewDataBinding
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.baselibrary.mvvm.ViewModelFragment
import com.ziqi.wanandroid.util.LoginManager
import com.ziqi.wanandroid.util.StartUtil

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


    /**
     * 登录的管理回调
     */
    private var mLoginListeners: ArrayList<LoginListener> = ArrayList()

    /**
     * 登录的请求状态码
     */
    private val REQUEST_LOGIN: Int = 1000

    abstract fun initView()

    abstract fun dealViewModel()

    fun toLogin(loginListener: LoginListener) {
        activity?.apply {
            StartUtil.startLoginFragment(this, this@BaseFragment, REQUEST_LOGIN, null)
        }
        mLoginListeners.add(loginListener)
    }

    public interface LoginListener {
        fun onSuccess()
        fun onCancel()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_LOGIN -> {
                if (resultCode == Activity.RESULT_OK) {
                    for (mLoginListener in mLoginListeners) {
                        mLoginListener.onSuccess()
                    }
                } else {
                    for (mLoginListener in mLoginListeners) {
                        mLoginListener.onCancel()
                    }
                }
                mLoginListeners.clear()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mLoginListeners.clear()
    }
}
