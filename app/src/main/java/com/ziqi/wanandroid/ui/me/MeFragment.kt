package com.ziqi.wanandroid.ui.me

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.databinding.ViewDataBinding
import com.ziqi.baselibrary.base.ZBaseFragment
import com.ziqi.wanandroid.R

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/15 11:37 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class MeFragment : ZBaseFragment<Parcelable, ViewDataBinding>() {


    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?): MeFragment {
            var mWBaseFragment = MeFragment()
            mWBaseFragment.arguments = bundle
            return mWBaseFragment
        }
    }

    override fun onClick(v: View?) {

    }

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun zVisibleToUser(isNewIntent: Boolean) {

    }


    override fun zLazyVisible() {
        super.zLazyVisible()
    }
}