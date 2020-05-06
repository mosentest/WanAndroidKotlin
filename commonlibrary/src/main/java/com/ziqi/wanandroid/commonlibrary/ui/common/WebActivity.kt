package com.ziqi.wanandroid.commonlibrary.ui.common

import android.os.Parcelable
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ziqi.baselibrary.base.ZBaseActivity
import com.ziqi.baselibrary.common.WebFragment

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/10 2:09 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class WebActivity : CommonActivity() {
    override fun createFragment(): Fragment? {
        return WebFragment.newInstance(intent?.extras)
    }

    override fun onClick(v: View?) {

    }

    override fun zVisibleToUser(isNewIntent: Boolean) {
    }
}