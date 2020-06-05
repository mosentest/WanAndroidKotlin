package com.ziqi.wanandroid.commonlibrary.util

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.ziqi.wanandroid.commonlibrary.ui.common.WebActivity
import com.ziqi.baselibrary.common.WebFragment
import com.ziqi.baselibrary.common.WebInfo
import com.ziqi.baselibrary.util.StartActivityCompat
import com.ziqi.wanandroid.commonlibrary.R
import com.ziqi.wanandroid.commonlibrary.ui.common.CommonActivity
import com.ziqi.wanandroid.commonlibrary.ui.globaldialog.GlobalActivity
import com.ziqi.wanandroid.commonlibrary.ui.globaldialog.GlobalFragment
import com.ziqi.wanandroid.commonlibrary.ui.globaldialog.GlobalParams
import com.ziqi.wanandroid.commonlibrary.ui.imagepreview.ImagePreviewActivity
import com.ziqi.wanandroid.commonlibrary.ui.imagepreview.ImagePreviewFragment
import com.ziqi.wanandroid.commonlibrary.ui.imagepreview.ImagePreviewParams
import com.ziqi.wanandroid.commonlibrary.ui.login.LoginActivity
import com.ziqi.wanandroid.commonlibrary.ui.login.LoginFragment
import com.ziqi.wanandroid.commonlibrary.ui.register.RegisterActivity
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

        @JvmStatic
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

        @JvmStatic
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

        @JvmStatic
        fun startWxArticleFragment(
            context: Context,
            fragment: Fragment?,
            requestCode: Int = -1,
            info: Parcelable?
        ) {
            StartPage.toWxArticle(context, fragment, requestCode, info)
        }


        @JvmStatic
        fun startCollectFragment(
            context: Context,
            fragment: Fragment?,
            requestCode: Int = -1,
            info: Parcelable?
        ) {
            StartPage.toCollect(context, fragment, requestCode, info)
        }

        @JvmStatic
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
                context.getString(R.string.common_text_login),
                true,
                info
            )
        }

        @JvmStatic
        fun startRegisterFragment(
            context: Context,
            fragment: Fragment?,
            requestCode: Int = -1,
            info: Parcelable?
        ) {
            StartActivityCompat.startActivity(
                context,
                fragment,
                RegisterFragment::class.java.name,
                requestCode,
                Intent(context, RegisterActivity::class.java),
                context.getString(R.string.common_text_register),
                true,
                info
            )
        }

        /**
         * 启动全局的对话框
         */
        @JvmStatic
        fun startGlobalFragment(
            context: Context,
            fragment: Fragment?,
            requestCode: Int = -1,
            info: GlobalParams
        ) {
            StartActivityCompat.startActivity(
                context,
                fragment,
                GlobalFragment::class.java.name,
                requestCode,
                Intent(context, GlobalActivity::class.java),
                "",
                false,
                info
            )
        }
    }

}