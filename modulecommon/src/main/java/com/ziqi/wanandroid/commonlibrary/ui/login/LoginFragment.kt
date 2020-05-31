package com.ziqi.wanandroid.commonlibrary.ui.login

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.consumer.StretchConsumer
import com.ziqi.baselibrary.util.StringUtil
import com.ziqi.wanandroid.commonlibrary.R
import com.ziqi.wanandroid.commonlibrary.databinding.FragmentLoginBinding
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseFragment
import com.ziqi.wanandroid.commonlibrary.util.LoginManager
import com.ziqi.wanandroid.commonlibrary.util.StartUtil
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
        //仿MIUI的弹性拉伸效果：
        //侧滑时表现为弹性拉伸效果，结束后自动恢复
        SmartSwipe.wrap(view)
            .addConsumer(StretchConsumer())
            .enableVertical(); //工作方向：纵向
    }

    override fun dealViewModel() {
        mViewModel?.mLogin?.observe(viewLifecycleOwner, Observer {
            LoginManager.saveUser(it)
            if (LoginManager.isLogin()) {
                zToastShort(-1, getString(R.string.common_login_success))
                activity?.setResult(Activity.RESULT_OK)
                activity?.onBackPressed()
            }
        })
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
                mViewDataBinding?.apply {
                    val userName = etUserName.text.toString().trim()
                    val password = etPassword.text.toString().trim()
                    if (StringUtil.isEmpty(userName)) {
                        zToastShort(-1, getString(R.string.common_hint_input_account))
                        return
                    }
                    if (StringUtil.isEmpty(password)) {
                        zToastShort(-1, getString(R.string.common_hint_input_password))
                        return
                    }
                    mViewModel?.login(userName, password)
                }
            }
            R.id.tvRegister -> {
                activity?.apply {
                    StartUtil.startRegisterFragment(this, this@LoginFragment, -1, null)
                }
            }
        }
    }

    override fun zVisibleToUser(isNewIntent: Boolean) {
        mViewDataBinding?.listener = this
        initView()
        dealViewModel()
        onRefresh()
    }

    override fun onRefresh() {

    }

}
