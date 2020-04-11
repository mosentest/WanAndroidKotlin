package com.ziqi.baselibrary.http

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/11 10:10 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class ResponseException(private val code: Int, private val msg: String) : Exception() {

}