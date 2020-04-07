package com.ziqi.baselibrary.base.interfaces

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/6 11:21 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
interface IBaseView : IView, IStatusView, IToastView {

    /**
     * 是否打开eventBus
     */
    fun zIsEventBus(): Boolean = false

    /**
     * 是否使用DataBinding
     */
    fun zIsDataBinding(): Boolean = false

    fun zSetLayoutId(): Int = -1

    fun zVisibleToUser(isNewIntent: Boolean)
}