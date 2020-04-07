package com.ziqi.baselibrary.base.`interface`

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/6 12:07 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
interface IToastView {
    /**
     * 加载进度的dialog
     */
    fun zShowLoadingDialog(flag: Int, msg: String?)

    /**
     * 隐藏进度的dialog
     */
    fun zHideLoadingDialog(flag: Int)

    /**
     * 展示toast Long
     */
    fun zToastLong(flag: Int, msg: String?)

    /**
     * 展示toast Short
     */
    fun zToastShort(flag: Int, msg: String?)

    /**
     * 弹确认信息的对话框
     */
    fun zConfirmDialog(flag: Int, msg: String?)

}