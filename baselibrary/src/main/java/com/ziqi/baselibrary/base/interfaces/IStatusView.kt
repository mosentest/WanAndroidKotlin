package com.ziqi.baselibrary.base.interfaces

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
     * 没数据view
     */
    fun zStatusEmptyView()

    /**
     * 错误信息view
     */
    fun zStatusErrorView()

    /**
     * 正常view
     */
    fun zStatusContentView()
}