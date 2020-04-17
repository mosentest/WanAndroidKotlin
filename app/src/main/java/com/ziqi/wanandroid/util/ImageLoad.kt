package com.ziqi.wanandroid.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ziqi.baselibrary.util.LogUtil
import okio.JvmOverloads
import java.lang.Exception

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/16 4:25 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object ImageLoad {

    fun loadUrl(context: Context, url: String, imageView: ImageView) {
        try {
            Glide.with(context).load(url).into(imageView)
        } catch (e: Exception) {
            LogUtil.e("loadUrl", e)
        }
    }

}