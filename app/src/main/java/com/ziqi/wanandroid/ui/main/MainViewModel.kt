package com.ziqi.wanandroid.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.ziqi.baselibrary.livedata.Event
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.baselibrary.util.SystemTool
import com.ziqi.wanandroid.BuildConfig
import com.ziqi.wanandroid.bean.Article
import com.ziqi.wanandroid.bean.Banner
import com.ziqi.wanandroid.bean.WanResponse
import com.ziqi.wanandroid.net.NetRepository
import com.ziqi.wanandroid.util.asyncExt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/15 4:26 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class MainViewModel(ctx: Application) : AndroidViewModel(ctx) {

    private val TAG: String = MainViewModel::class.java.simpleName

}