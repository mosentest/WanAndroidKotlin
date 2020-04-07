package com.ziqi.baselibrary.base.`interface`

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/6 12:08 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
interface IStatusView {

    /**
     * 加载view
     */
    fun zStatusLoadingView()

    /**
     * 没网络view
     */
    fun zStatusNetWorkView()

    /**
     * 错误信息view
     */
    fun zStatusErrorView(type: Int, msg: String?)

    /**
     * 正常view
     */
    fun zStatusContentView()
}