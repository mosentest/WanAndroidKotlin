package com.ziqi.wanandroid.commonlibrary.ui.login

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.ziqi.wanandroid.commonlibrary.R
import com.ziqi.wanandroid.commonlibrary.databinding.FragmentLoginBinding
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseFragment
import com.ziqi.wanandroid.commonlibrary.util.route.StartPage

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
        if (mStartParams != null && mStartParams!!.isInvalid) {
            activity?.apply {
                StartPage.toMain(this)
                finish()
            }
            return true
        }
        return super.onInterceptBackPressed()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvLogin -> {
                zToastShort(-1, "登陆成功")
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
