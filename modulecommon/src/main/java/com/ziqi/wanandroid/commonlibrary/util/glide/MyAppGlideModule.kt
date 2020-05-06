package com.ziqi.wanandroid.commonlibrary.util.glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/16 5:41 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
@GlideModule   //必须添加注释，然后继承下面的 AppGlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //这里自己搞
    }
}