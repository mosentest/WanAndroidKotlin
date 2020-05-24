package com.ziqi.baselibrary.http.error

import java.io.IOException

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/11 10:10 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class ResponseThrowable : IOException {
    var code: Int
    var errMsg: String?

    constructor(error: ERROR, e: Throwable? = null) : super(e) {
        code = error.getKey()
        errMsg = error.getValue()
    }

    constructor(code: Int, msg: String?) : super() {
        this.code = code
        this.errMsg = msg
    }

    constructor(code: Int, msg: String?, e: Throwable? = null) : super(e) {
        this.code = code
        this.errMsg = msg
    }
}