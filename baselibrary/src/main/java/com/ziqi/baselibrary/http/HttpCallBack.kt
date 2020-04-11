package com.ziqi.baselibrary.http

import com.google.gson.internal.`$Gson$Types`
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class HttpCallBack<T> {
    var mType: Type? = null
    fun start() {}
    abstract fun success(t: T)
    abstract fun error(throwable: Throwable)
    fun end() {}

    init { //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
        val superclass = javaClass.genericSuperclass
        mType = if (superclass is Class<*>) {
            null
        } else { //ParameterizedType参数化类型，即泛型
            val parameterized = superclass as ParameterizedType?
            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            //将Java 中的Type实现,转化为自己内部的数据实现,得到gson解析需要的泛型
            `$Gson$Types`.canonicalize(parameterized!!.actualTypeArguments[0])
        }
    }
}