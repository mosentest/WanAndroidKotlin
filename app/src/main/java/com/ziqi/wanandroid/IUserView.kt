package com.ziqi.wanandroid

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/6 12:06 PM
 * Description:
 * History: 这里的逻辑按例需要回调的
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
interface IUserView {

    /**
     * 去登录
     */
    fun zLogin()

    /**
     * 去注册
     */
    fun zRegister()

    /**
     * 获取用户信息
     */
    fun zGetUserInfo()

    /**
     * 去支付
     */
    fun zPay()

    /**
     * 拨打电话
     */
    fun zOpenCall(contact: String)

    /**
     * 打开内置浏览器
     */
    fun zOpenWebView(url: String)

    /**
     * 打开相机/或者第三方的库
     */
    fun zOpenCamera(type: Int)

    /**
     * 分享
     */
    fun zShare(rId: Int, title: String, content: String)

    /**
     * 打开定位并获取定位信息
     */
    fun zGetLocation()

    /**
     * 打开定位导航
     */
    fun zOpenNavigation(lat: Long, lon: Long, address: String)
}