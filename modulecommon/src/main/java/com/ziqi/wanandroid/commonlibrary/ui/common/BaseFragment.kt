package com.ziqi.wanandroid.commonlibrary.ui.common

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ziqi.baselibrary.mvvm.ZViewModelFragment
import com.ziqi.baselibrary.util.statusbar.StatusBarUtil
import com.ziqi.wanandroid.commonlibrary.R
import com.ziqi.wanandroid.commonlibrary.ui.globaldialog.GlobalParams
import com.ziqi.wanandroid.commonlibrary.ui.login.LoginParams
import com.ziqi.wanandroid.commonlibrary.util.LoginManager
import com.ziqi.wanandroid.commonlibrary.util.StartUtil

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
    ZViewModelFragment<VM, StartParams, Binding>(),
    SwipeRefreshLayout.OnRefreshListener {


    /**
     * 登录的管理回调
     */
    private var mLoginListeners: ArrayList<LoginListener> = ArrayList()

    /**
     * 登录的请求状态码
     */
    private val REQUEST_LOGIN = 900

    private val REQUEST_GLOBAL = 901

    abstract fun initView()

    abstract fun dealViewModel()

    override fun initViewModel() {
        /**
         * 弹全局登陆的对话框
         */
        mViewModel?.mToLogin?.observe(viewLifecycleOwner, Observer {
            it?.apply {
                val contentIfNotHandled = getContentIfNotHandled()
                contentIfNotHandled?.apply {
                    activity?.apply {
                        StartUtil.startGlobalFragment(
                            this,
                            this@BaseFragment,
                            REQUEST_GLOBAL,
                            GlobalParams().apply {
                                this.content = errMsg
                                this.title = getString(R.string.common_dialog_title)
                                this.left = ""
                                this.right = getString(R.string.common_text_ok)
                                this.type = 1
                            })
                    }
                }
            }
        })
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
            REQUEST_GLOBAL -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mLoginListeners.clear()
    }

    fun toLogin(loginListener: LoginListener, loginParams: LoginParams?) {
        if (LoginManager.isLogin()) {
            loginListener.onSuccess()
            return
        }
        activity?.apply {
            StartUtil.startLoginFragment(this, this@BaseFragment, REQUEST_LOGIN, loginParams)
        }
        mLoginListeners.add(loginListener)
    }

    public interface LoginListener {
        fun onSuccess()
        fun onCancel()
    }
}
