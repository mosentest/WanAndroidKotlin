package com.ziqi.wanandroid.util

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.ziqi.wanandroid.ui.common.WebActivity
import com.ziqi.baselibrary.common.WebFragment
import com.ziqi.baselibrary.common.WebInfo
import com.ziqi.baselibrary.util.StartActivityCompat

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
    }

}