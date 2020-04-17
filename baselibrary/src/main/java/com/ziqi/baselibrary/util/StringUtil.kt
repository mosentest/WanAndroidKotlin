package com.ziqi.baselibrary.util

import java.util.*

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/16 3:40 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object StringUtil {
    fun isEmpty(text: String?): Boolean {
        return text == null || "".equals(text) || "null".equals(text.toLowerCase(Locale.CHINA))
    }

    fun emptyTip(text: String?, defaultText: String): String {
        return if (isEmpty(text)) defaultText else text!!
    }
}