package com.ziqi.wanandroid.commonlibrary.util

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.ziqi.wanandroid.commonlibrary.ui.common.WebActivity
import com.ziqi.baselibrary.common.WebFragment
import com.ziqi.baselibrary.common.WebInfo
import com.ziqi.baselibrary.util.StartActivityCompat
import com.ziqi.wanandroid.commonlibrary.ui.common.CommonActivity
import com.ziqi.wanandroid.commonlibrary.ui.imagepreview.ImagePreviewActivity
import com.ziqi.wanandroid.commonlibrary.ui.imagepreview.ImagePreviewFragment
import com.ziqi.wanandroid.commonlibrary.ui.imagepreview.ImagePreviewParams
import com.ziqi.wanandroid.commonlibrary.ui.login.LoginActivity
import com.ziqi.wanandroid.commonlibrary.ui.login.LoginFragment
import com.ziqi.wanandroid.commonlibrary.ui.register.RegisterFragment
import com.ziqi.wanandroid.commonlibrary.util.route.StartPage

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/10 2:37 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class StartUtil {
    companion object {
        fun startWebFragment(
            context: Context,
            fragment: Fragment?,
            requestCode: Int = -1,
            webInfo: WebInfo
        ) {
            StartActivityCompat.startActivity(
                context,
                fragment,
                WebFragment::class.java.name,
                requestCode,
                Intent(context, WebActivity::class.java),
                webInfo.url,
                true,
                webInfo
            )
        }

        fun startImagePreviewFragment(
            context: Context,
            fragment: Fragment?,
            requestCode: Int = -1,
            info: ImagePreviewParams
        ) {
            StartActivityCompat.startActivity(
                context,
                fragment,
                ImagePreviewFragment::class.java.name,
                requestCode,
                Intent(context, ImagePreviewActivity::class.java),
                "",
                true,
                info
            )
        }

        fun startWxArticleFragment(
            context: Context,
            fragment: Fragment?,
            requestCode: Int = -1,
            info: Parcelable?
        ) {
            StartPage.toWxArticle(context, fragment, requestCode, info)
        }

        fun startLoginFragment(
            context: Context,
            fragment: Fragment?,
            requestCode: Int = -1,
            info: Parcelable?
        ) {
            StartActivityCompat.startActivity(
                context,
                fragment,
                LoginFragment::class.java.name,
                requestCode,
                Intent(context, LoginActivity::class.java),
                "登录",
                true,
                info
            )
        }

        fun startRegisterFragment(
            context: Context,
            fragment: Fragment?,
            requestCode: Int = -1,
            info: Parcelable
        ) {
            StartActivityCompat.startActivity(
                context,
                fragment,
                RegisterFragment::class.java.name,
                requestCode,
                Intent(context, CommonActivity::class.java),
                "注册",
                true,
                info
            )
        }
    }

}