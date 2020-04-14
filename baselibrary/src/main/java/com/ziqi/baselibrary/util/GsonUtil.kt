package com.ziqi.baselibrary.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder




/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/11 9:49 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object GsonUtil {
    var gson: Gson = GsonBuilder() //配置你的Gson
        .setDateFormat("yyyy-MM-dd hh:mm:ss")
        .create()
}