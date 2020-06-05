package com.ziqi.wanandroid.commonlibrary.util

import com.ziqi.baselibrary.http.cookie.PersistentCookieStore
import com.ziqi.baselibrary.util.ContextUtils
import com.ziqi.baselibrary.util.FileUtil
import com.ziqi.baselibrary.util.StringUtil
import com.ziqi.baselibrary.util.io.KVUtils
import com.ziqi.wanandroid.commonlibrary.bean.User
import java.io.File

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/3 6:56 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object LoginManager {

    fun isLogin(): Boolean {
        return !StringUtil.isEmpty(getUser()?.id)
    }

    fun isNoLogin(): Boolean {
        return !isLogin()
    }

    fun saveUser(user: User) {
        KVUtils.instance.put(KVUtils.KEY_USER_INFO, user)
    }

    fun getUser(): User? {
        return KVUtils.instance.get(KVUtils.KEY_USER_INFO, User())
    }

    fun delUser() {
        KVUtils.instance.remove(KVUtils.KEY_USER_INFO)
    }

    /**
     * 退出登陆
     */
    fun logout() {
        delUser()
        //移除cookie上的所有内容
        PersistentCookieStore.getInstance(ContextUtils.context).removeAll()
        val httpCacheDirectory = File(ContextUtils.context?.cacheDir, "okhttpCache")
        FileUtil.deleteDirWithFile(httpCacheDirectory)
    }

}