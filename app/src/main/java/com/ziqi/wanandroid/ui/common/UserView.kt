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
    override fun zLogin() {

    }

    override fun zRegister() {
    }

    override fun zGetUserInfo() {
    }

    override fun zPay() {
    }

    override fun zOpenCall(contact: String) {
    }

    override fun zOpenWebView(url: String) {
    }

    override fun zOpenCamera(type: Int) {
    }

    override fun zShare(rId: Int, title: String, content: String) {
    }

    override fun zGetLocation() {
    }

    override fun zOpenNavigation(lat: Long, lon: Long, address: String) {
    }
}