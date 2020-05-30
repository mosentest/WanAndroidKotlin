package com.ziqi.wanandroid.commonlibrary.util.io

import android.content.Context

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/30 4:29 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class KVUtils : IKV {

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = KVUtils()
    }

    override fun init(context: Context?) {
        MMKVUtils.instance.init(context)
    }

    override fun put(context: Context, key: String?, `object`: Any) {
        MMKVUtils.instance.put(context, key, `object`)
    }

    override fun <T> get(context: Context, key: String?, defaultObject: T): T? {
        return MMKVUtils.instance.get(context, key, defaultObject)
    }

    override fun remove(context: Context, key: String?) {
        MMKVUtils.instance.remove(context, key)
    }

}