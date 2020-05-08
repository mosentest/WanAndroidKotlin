package com.ziqi.wanandroid.commonlibrary.ui.common

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.baselibrary.mvvm.ViewModelFragment
import com.ziqi.baselibrary.util.StartActivityCompat
import com.ziqi.wanandroid.commonlibrary.ui.globaldialog.GlobalParams
import com.ziqi.wanandroid.commonlibrary.ui.login.LoginParams
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
abstract class BaseFragment<VM : UserViewModel, StartParams : Parcelable, Binding : ViewDataBinding> :
    ViewModelFragment<VM, StartParams, Binding>(),
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

    fun toLogin(loginListener: LoginListener) {
        toLogin(loginListener, null)
    }

    fun toLogin(loginListener: LoginListener, loginParams: LoginParams?) {
        activity?.apply {
            StartUtil.startLoginFragment(this, this@BaseFragment, REQUEST_LOGIN, loginParams)
        }
        mLoginListeners.add(loginListener)
    }

    public interface LoginListener {
        fun onSuccess()
        fun onCancel()
    }

    override fun initViewModel() {
        mViewModel?.mToLogin?.observe(viewLifecycleOwner, Observer {
            it?.apply {
                if (getContentIfNotHandled() == true) {
                    activity?.apply {
                        StartUtil.startGlobalFragment(
                            this,
                            this@BaseFragment,
                            REQUEST_GLOBAL,
                            GlobalParams().apply {
                                this.content = "重新登录"
                                this.title = "温馨提示"
                                this.left = ""
                                this.right = "确定"
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
                if (resultCode == Activity.RESULT_OK) {
                    val globalParams: GlobalParams? =
                        data?.getParcelableExtra(StartActivityCompat.NEXT_PARCELABLE)
                    globalParams?.apply {
                        when (type) {
                            1 -> {
                                //登录成功
                            }
                            2 -> {

                            }
                        }
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mLoginListeners.clear()
    }
}
