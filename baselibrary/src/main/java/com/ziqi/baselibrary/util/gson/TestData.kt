package com.ziqi.baselibrary.util.gson

/**
 *    作者 : moziqi
 *    邮箱 : 709847739@qq.com
 *    时间   : 2020/6/8-10:03
 *    desc   :
 *    version: 1.0
 */
class TestData {
    var tempInt: Int? = 0
    var tempString: String? = ""
    var tempA: A? = null
    var tempAs: List<A>? = null

    class A {
        var tempInt: Int? = 0
        var tempString: String? = ""
        override fun toString(): String {
            return "A(tempInt=$tempInt, tempString=$tempString)"
        }
    }
}