package com.ziqi.wanandroid.ui.me

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.databinding.ViewDataBinding
import com.ziqi.baselibrary.base.ZBaseFragment
import com.ziqi.wanandroid.R
import com.ziqi.wanandroid.databinding.FragmentMeBinding
import com.ziqi.wanandroid.ui.common.BaseFragment
import com.ziqi.wanandroid.util.LoginManager

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/15 11:37 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class MeFragment : BaseFragment<MeViewModel, Parcelable, FragmentMeBinding>() {


    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?): MeFragment {
            var mWBaseFragment = MeFragment()
            mWBaseFragment.arguments = bundle
            return mWBaseFragment
        }
    }

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvNoLogin -> {
                activity?.apply {
                    LoginManager.toLogin(this, this@MeFragment)
                }
            }
            R.id.tvSearch -> {

            }
            R.id.tvWxArticle -> {

            }
            R.id.tvCollect -> {

            }
            R.id.tvUserArticle -> {

            }
            R.id.tvWenda -> {

            }
        }
    }

    override fun zVisibleToUser(isNewIntent: Boolean) {

    }

    override fun zLazyVisible() {
        super.zLazyVisible()
        mViewDataBinding?.listener = this
    }

    override fun onResume() {
        super.onResume()
        mViewDataBinding?.tvNoLogin?.visibility =
            if (LoginManager.isNoLogin()) View.VISIBLE else View.GONE
        mViewDataBinding?.llLogin?.visibility =
            if (LoginManager.isLogin()) View.VISIBLE else View.GONE
        mViewDataBinding?.tvLogout?.visibility =
            if (LoginManager.isLogin()) View.VISIBLE else View.GONE
    }

    override fun onRefresh() {

    }


    override fun initView() {

    }

    override fun dealViewModel() {

    }
}