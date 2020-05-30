package com.ziqi.wanandroid.commonlibrary.util.io

import android.content.Context
import android.os.Parcelable
import android.util.Log
import com.tencent.mmkv.MMKV


/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/30 4:40 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class MMKVUtils : IKV {

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = MMKVUtils()
    }

    override fun init(context: Context?) {
        val rootDir = MMKV.initialize(context)
        Log.i("MMKVUtils", "mmkv root: " + rootDir)
    }

    override fun put(context: Context, key: String?, `object`: Any) {
        val kv = MMKV.defaultMMKV()
        if (`object` is String) {
            kv.encode(key, `object`)
        } else if (`object` is Int) {
            kv.encode(key, `object`)
        } else if (`object` is Boolean) {
            kv.encode(key, `object`)
        } else if (`object` is Float) {
            kv.encode(key, `object`)
        } else if (`object` is Long) {
            kv.encode(key, `object`)
        } else if (`object` is Parcelable) {
            kv.encode(key, `object`)
        } else {
            kv.encode(key, `object`.toString())
        }
    }

    override fun <T> get(context: Context, key: String?, defaultObject: T): T? {
        val kv = MMKV.defaultMMKV()
        if (defaultObject is String) {
            return kv.decodeString(key, defaultObject as String) as T?
        } else if (defaultObject is Int) {
            val anInt = kv.decodeInt(key, (defaultObject as Int))
            return anInt as T
        } else if (defaultObject is Boolean) {
            val aBoolean = kv.decodeBool(key, (defaultObject as Boolean))
            return aBoolean as T
        } else if (defaultObject is Float) {
            val aFloat = kv.decodeFloat(key, (defaultObject as Float))
            return aFloat as T
        } else if (defaultObject is Long) {
            val aLong = kv.decodeLong(key, (defaultObject as Long))
            return aLong as T
        } else if (defaultObject is Parcelable) {
            val parcelable = kv.decodeParcelable(key, (defaultObject as Parcelable).javaClass)
            return parcelable as T
        }
        return null
    }

    override fun remove(context: Context, key: String?) {
        val kv = MMKV.defaultMMKV()
        kv.reKey(key)
    }

}