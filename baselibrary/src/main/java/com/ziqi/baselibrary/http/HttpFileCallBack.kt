package com.ziqi.baselibrary.http

import java.io.File

/**
 * 下载文件返回
 */
abstract class HttpFileCallBack {
    fun start() {}
    abstract fun progress(size: Int)
    abstract fun success(file: File?)
    abstract fun error(throwable: Throwable)
    fun end() {}
}