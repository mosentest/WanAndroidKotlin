package com.ziqi.wanandroid.commonlibrary.ui.common

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ziqi.baselibrary.http.error.ResponseThrowable
import com.ziqi.baselibrary.livedata.Event
import com.ziqi.baselibrary.mvvm.ZBaseViewModel

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/4 3:19 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
open class BaseViewModel(ctx: Application) : ZBaseViewModel(ctx) {
    //查询用户信息的viewModel

    /**
     * 接口控制需要去登录
     */
    var mToLogin: MutableLiveData<Event<Boolean>> = MutableLiveData()

    var mToUpdate: MutableLiveData<Event<Boolean>> = MutableLiveData()

    fun errorInfo(rt: ResponseThrowable?): String? {
        return rt?.let {
            var msg: String? = null
            //errorCode = -1001 代表登录失效，需要重新登录。
            //errorCode = -1002 代表更新，假设
            if (it.code == -1001) {
                mToLogin.value = Event(true)
            } else if (it.code == -1002) {
                //弹出检查更新全局对话框
                mToUpdate.value = Event(true)
            } else {
                //其他情况才有msg
                msg = """${it.errMsg}[${rt.code}]"""
            }
            msg
        }
    }
}