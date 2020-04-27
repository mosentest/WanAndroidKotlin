package com.ziqi.baselibrary.http.error

/**
 *    作者 : moziqi
 *    邮箱 : 709847739@qq.com
 *    时间   : 2020/4/26-14:35
 *    desc   :
 *    version: 1.0
 */
enum class ERROR(private val code: Int, private val err: String) {

    /**
     * 未知错误
     */
    UNKNOWN(1000, "未知错误"),
    /**
     * 解析错误
     */
    PARSE_ERROR(1001, "解析错误"),
    /**
     * 网络错误
     */
    NETWORD_ERROR(1002, "网络错误"),
    /**
     * 协议出错
     */
    HTTP_ERROR(1003, "协议错误"),

    /**
     * 证书出错
     */
    SSL_ERROR(1004, "证书错误"),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1006, "连接超时");

    fun getValue(): String {
        return err
    }

    fun getKey(): Int {
        return code
    }

}