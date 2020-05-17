package com.ziqi.wanandroid.commonlibrary.util.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream


/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/16 5:41 PM
 * Description:
 * History:
 *
 * 参考：
 * https://blog.csdn.net/gpf1320253667/article/details/95049226
 * https://github.com/sunfusheng/GlideImageView
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
@GlideModule   //必须添加注释，然后继承下面的 AppGlideModule
class MyAppGlideModule : AppGlideModule() {

    override fun registerComponents(
        context: Context,
        glide: Glide,
        registry: Registry
    ) {
        super.registerComponents(context, glide, registry)
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient())
        )
    }
}