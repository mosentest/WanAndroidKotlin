package com.ziqi.baselibrary.http.retrofit

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/14 4:06 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class StringConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (type === String::class.java) {
            return StringConverter()
        }
        //其它类型我们不处理，返回null就行
        return super.responseBodyConverter(type, annotations, retrofit)
    }

    class StringConverter : Converter<ResponseBody, String> {
        override fun convert(value: ResponseBody): String? {
            return value.string()
        }
    }
}