package com.ziqi.wanandroid.util

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.ziqi.baselibrary.util.LogUtil
import java.io.File
import java.util.concurrent.ExecutionException


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
            LogUtil.e("context.loadUrl", e)
        }
    }

    fun loadUrl(fragment: Fragment, url: String, imageView: ImageView) {
        try {
            Glide.with(fragment).load(url).into(imageView)
        } catch (e: Exception) {
            LogUtil.e("fragment.loadUrl", e)
        }
    }

    /**
     * https://www.jianshu.com/p/b5246e210b07
     */
    fun getImagePathFromCache(
        fragment: Fragment,
        url: String?,
        expectW: Int,
        expectH: Int
    ): String? {
        val future: FutureTarget<File> =
            Glide.with(fragment).load(url).downloadOnly(expectW, expectH)
        try {
            val cacheFile: File = future.get()
            return cacheFile.getAbsolutePath()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
        return null
    }

}