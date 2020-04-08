package com.ziqi.baselibrary.base.interfaces

import android.os.Bundle

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/6 11:37 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
interface IBaseFragment {
    fun onInterceptBackPressed(): Boolean = false

    fun onNewIntent(bundle: Bundle?)
}