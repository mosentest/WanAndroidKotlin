package com.ziqi.wanandroid.ui.login

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.ziqi.wanandroid.R
import com.ziqi.wanandroid.databinding.FragmentLoginBinding
import com.ziqi.wanandroid.ui.common.BaseFragment

class LoginFragment : BaseFragment<LoginViewModel, Parcelable, FragmentLoginBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?) = LoginFragment().apply {
            arguments = bundle
        }
    }

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {

    }

    override fun dealViewModel() {

    }

    override fun onClick(v: View?) {

    }

    override fun zVisibleToUser(isNewIntent: Boolean) {
    }

    override fun onRefresh() {

    }

}
