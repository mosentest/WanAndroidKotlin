package com.ziqi.wanandroid.commonlibrary.ui.register

import android.os.Bundle
import android.os.Parcelable
import android.view.View
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
