package com.ziqi.wanandroid.commonlibrary.util.io

import android.content.Context

/**
 * https://blog.csdn.net/zy517863543/article/details/53783659?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase
 */
class SPUtils : IKV {

    companion object {
        val instance = SingletonHolder.holder
        /**
         * 保存在手机里面的文件名
         */
        const val FILE_NAME = "zzz_sp_config"
    }

    private object SingletonHolder {
        val holder = SPUtils()
    }

    override fun init(context: Context?) {

    }

    /**
     * 保存数据的方法，需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    override fun put(
        context: Context,
        key: String?,
        `object`: Any
    ) {
        val sp =
            context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        if (`object` is String) {
            editor.putString(key, `object`)
        } else if (`object` is Int) {
            editor.putInt(key, `object`)
        } else if (`object` is Boolean) {
            editor.putBoolean(key, `object`)
        } else if (`object` is Float) {
            editor.putFloat(key, `object`)
        } else if (`object` is Long) {
            editor.putLong(key, `object`)
        } else {
            editor.putString(key, `object`.toString())
        }
        editor.apply()
    }

    /**
     * 得到保存数据的方法，更改根据输入的默认类型得到保存的数据的类型
     *
     * @param context       上下文
     * @param key           保存的名称
     * @param defaultObject 输入默认类型，传类型的默认值即可，  String：""; int:0 ;boolean:false
     * 需要注意的是，如果没有保存获取的值，则返回输入的默认类型的值，比如get(this,"pwd",0);如果没有pwd对应的值，则返回0
     * @return
     */
    override fun <T> get(context: Context, key: String?, defaultObject: T): T? {
        val sp =
            context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        if (defaultObject is String) {
            return sp.getString(key, defaultObject as String) as T?
        } else if (defaultObject is Int) {
            val anInt = sp.getInt(key, (defaultObject as Int))
            return anInt as T
        } else if (defaultObject is Boolean) {
            val aBoolean = sp.getBoolean(key, (defaultObject as Boolean))
            return aBoolean as T
        } else if (defaultObject is Float) {
            val aFloat = sp.getFloat(key, (defaultObject as Float))
            return aFloat as T
        } else if (defaultObject is Long) {
            val aLong = sp.getLong(key, (defaultObject as Long))
            return aLong as T
        }
        return null
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    override fun remove(context: Context, key: String?) {
        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key).apply()
    }
}