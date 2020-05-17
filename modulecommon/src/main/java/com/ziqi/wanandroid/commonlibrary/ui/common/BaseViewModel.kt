package com.ziqi.wanandroid.commonlibrary.ui.common

import android.app.Application
import androidx.lifecycle.LiveData
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
    private val _mToLogin: MutableLiveData<Event<ResponseThrowable>> = MutableLiveData()
    val mToLogin: LiveData<Event<ResponseThrowable>>
        get() = _mToLogin


    private val _mToUpdate: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val mToUpdate: LiveData<Event<Boolean>>
        get() = _mToUpdate

    fun errorInfo(rt: ResponseThrowable?): String? {
        return rt?.let {
            var msg: String? = null
            //errorCode = -1001 代表登录失效，需要重新登录。
            //errorCode = -1002 代表更新，假设
            if (it.code == -1001) {
                _mToLogin.value = Event(rt)
            } else if (it.code == -1002) {
                //弹出检查更新全局对话框
                _mToUpdate.value = Event(true)
            } else {
                //其他情况才有msg
                msg = """${it.errMsg}[${rt.code}]"""
            }
            msg
        }
    }


    fun toLogin(msg: String) {
        _mToLogin.value = Event(ResponseThrowable(-1000, msg))
    }
}