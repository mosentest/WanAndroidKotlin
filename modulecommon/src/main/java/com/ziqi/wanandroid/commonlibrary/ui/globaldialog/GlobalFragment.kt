package com.ziqi.wanandroid.commonlibrary.ui.globaldialog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ziqi.baselibrary.util.StartActivityCompat
import com.ziqi.baselibrary.util.StringUtil
import com.ziqi.wanandroid.commonlibrary.R
import com.ziqi.wanandroid.commonlibrary.databinding.FragmentGlobalBinding
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseFragment
import com.ziqi.wanandroid.commonlibrary.ui.common.UserViewModel
import com.ziqi.wanandroid.commonlibrary.ui.login.LoginParams

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/4 7:03 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class GlobalFragment : BaseFragment<UserViewModel, GlobalParams, FragmentGlobalBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?) = GlobalFragment().apply {
            arguments = bundle
        }
    }

    override fun initView() {

    }

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_global
    }

    override fun dealViewModel() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.left -> {
                activity?.apply {
                    finish()
                }
            }
            R.id.right -> {
                mBundleData?.apply {
                    when (type) {
                        1 -> {
                            //1是去登录
                            toLogin(object : LoginListener {
                                override fun onSuccess() {
                                    activity?.apply {
                                        //把启动的参数返回给上一个页面
                                        setResult(
                                            Activity.RESULT_OK,
                                            Intent().putExtra(
                                                StartActivityCompat.NEXT_PARCELABLE,
                                                mBundleData
                                            )
                                        )
                                        finish()
                                    }
                                }

                                override fun onCancel() {
                                }

                            }, LoginParams().apply {
                                isInvalid = true
                            })
                        }
                        2 -> {
                            //2是检查更新
                        }
                    }
                }
            }
        }

    }

    override fun zVisibleToUser(isNewIntent: Boolean) {

        mViewDataBinding?.listener = this

        mBundleData?.apply {

            mViewDataBinding?.content?.text = content
            mViewDataBinding?.title?.text = title
            mViewDataBinding?.left?.text = left
            mViewDataBinding?.right?.text = right

            mViewDataBinding?.title?.visibility =
                if (StringUtil.isEmpty(title)) View.GONE else View.VISIBLE
            mViewDataBinding?.content?.visibility =
                if (StringUtil.isEmpty(content)) View.GONE else View.VISIBLE

            mViewDataBinding?.left?.visibility =
                if (StringUtil.isEmpty(left)) View.GONE else View.VISIBLE
            mViewDataBinding?.right?.visibility =
                if (StringUtil.isEmpty(right)) View.GONE else View.VISIBLE

        }


    }

    override fun onRefresh() {

    }

}