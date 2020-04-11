package com.ziqi.wanandroid.ui.common

import androidx.lifecycle.LifecycleObserver

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/9 10:37 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class UserView : IUserView, LifecycleObserver {

    var loginLists: MutableList<IUserView.CallBack<*>> = mutableListOf()


    override fun <T> zLogin(allBack: IUserView.CallBack<T>) {
        if (loginLists.indexOf(allBack) != -1) {
            loginLists.add(allBack)
        }
    }

    override fun zRegister(allBack: IUserView.CallBack<Any>) {

    }

    override fun zGetUserInfo(allBack: IUserView.CallBack<Any>) {

    }

    override fun zPay(allBack: IUserView.CallBack<Any>) {

    }

    override fun zOpenCall(contact: String, allBack: IUserView.CallBack<Any>) {

    }

    override fun zOpenWebView(url: String, allBack: IUserView.CallBack<Any>) {

    }

    override fun zOpenCamera(type: Int, allBack: IUserView.CallBack<Any>) {

    }

    override fun zShare(
        rId: Int,
        title: String,
        content: String,
        allBack: IUserView.CallBack<Any>
    ) {

    }

    override fun zGetLocation(allBack: IUserView.CallBack<Any>) {

    }

    override fun zOpenNavigation(
        lat: Long,
        lon: Long,
        address: String,
        allBack: IUserView.CallBack<Any>
    ) {

    }


}