package com.ziqi.wanandroid.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.ziqi.wanandroid.R
import com.ziqi.wanandroid.databinding.FragmentLoginBinding
import com.ziqi.wanandroid.ui.common.BaseFragment
import com.ziqi.wanandroid.ui.main.MainActivity

class LoginFragment : BaseFragment<LoginViewModel, LoginParams, FragmentLoginBinding>() {

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

    override fun onInterceptBackPressed(): Boolean {
        if (mBundleData != null && mBundleData!!.isInvalid) {
            activity?.apply {
                startActivity(Intent(activity, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                })
                finish()
            }
            return true
        }
        return super.onInterceptBackPressed()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvLogin -> {
                activity?.setResult(Activity.RESULT_OK)
                activity?.onBackPressed()
            }
        }
    }

    override fun zVisibleToUser(isNewIntent: Boolean) {
        mViewDataBinding?.listener = this
    }

    override fun onRefresh() {

    }

}
