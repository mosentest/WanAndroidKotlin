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
    fun zLogin(flag: Int)

    /**
     * 去注册
     */
    fun zRegister(flag: Int)

    /**
     * 获取用户信息
     */
    fun zGetUserInfo(flag: Int)

    /**
     * 去支付
     */
    fun zPay(flag: Int)

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
    fun zOpenCamera(flag: Int)

    /**
     * 分享
     */
    fun zShare(flag: Int, rId: Int, title: String, content: String)

    /**
     * 打开定位并获取定位信息
     */
    fun zGetLocation()

    /**
     * 打开定位导航
     */
    fun zOpenNavigation(lat: Long, lon: Long, address: String)
}