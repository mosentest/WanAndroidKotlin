package com.ziqi.wanandroid.commonlibrary.ui.register

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.lifecycle.Observer
import com.ziqi.baselibrary.util.StringUtil
import com.ziqi.baselibrary.util.statusbar.StatusBarUtil
import com.ziqi.wanandroid.commonlibrary.R
import com.ziqi.wanandroid.commonlibrary.databinding.FragmentRegisterBinding
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseFragment

class RegisterFragment : BaseFragment<RegisterViewModel, Parcelable, FragmentRegisterBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?) = RegisterFragment().apply {
            arguments = bundle
        }
    }

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_register
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvRegister -> {
                mViewDataBinding?.apply {
                    val userName = etUserName.text.toString().trim()
                    val password = etPassword.text.toString().trim()
                    val repassword = etRePassword.text.toString().trim()
                    if (StringUtil.isEmpty(userName)) {
                        zToastShort(-1, getString(R.string.common_hint_input_account))
                        return
                    }
                    if (StringUtil.isEmpty(password)) {
                        zToastShort(-1, getString(R.string.common_hint_input_password))
                        return
                    }
                    if (password != repassword) {
                        zToastShort(-1, getString(R.string.common_hint_input_password_error))
                        return
                    }
                    mViewModel?.register(userName, password, repassword)
                }
            }
            else -> {

            }
        }
    }

    override fun zVisibleToUser(isNewIntent: Boolean) {
        activity?.apply {
            StatusBarUtil.setTranslucentStatus(this)
            StatusBarUtil.setStatusBarDarkTheme(this, true)
        }
        mViewDataBinding?.listener = this
        initView()
        dealViewModel()
    }

    override fun initView() {

    }

    override fun dealViewModel() {
        mViewModel?.register?.observe(this, Observer {
            zToastShort(-1, getString(R.string.common_register_success))
            activity?.finish()
        })
    }

    override fun onRefresh() {

    }

}
