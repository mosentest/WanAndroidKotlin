package com.ziqi.baselibrary.util.io

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

        const val KEY_USER_INFO: String = "key_user_info"
    }

    private object SingletonHolder {
        val holder = KVUtils()
    }

    override fun init(context: Context?) {
        MMKVUtils.instance.init(context)
    }

    override fun put(key: String?, `object`: Any) {
        MMKVUtils.instance.put(key, `object`)
    }

    override fun <T> get(key: String?, defaultObject: T): T? {
        return MMKVUtils.instance.get(key, defaultObject)
    }

    override fun remove(key: String?) {
        MMKVUtils.instance.remove(key)
    }

}