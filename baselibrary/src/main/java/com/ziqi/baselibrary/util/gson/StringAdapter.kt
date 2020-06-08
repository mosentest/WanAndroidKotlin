package com.ziqi.baselibrary.util.gson

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/6/7 3:12 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class StringAdapter : TypeAdapter<String?>() {
    override fun write(out: JsonWriter?, value: String?) {
        try {
            var tempValue: String? = value
            if (tempValue == null) {
                tempValue = ""
            }
            out?.value(tempValue)
        } catch (e: Exception) {

        }
    }

    override fun read(`in`: JsonReader?): String? {
        var value: String? = ""
        try {
            if (`in`?.peek() == JsonToken.NULL) {
                `in`.nextNull()
                value = ""
            } else if (`in`?.peek() == JsonToken.BOOLEAN) {
                val b = `in`.nextBoolean()
                value = b.toString()
            } else if (`in`?.peek() == JsonToken.NUMBER) {
                try {
                    value = `in`.nextLong().toString()
                } catch (e: Exception) {
                    value = ""
                }
            } else {
                value = `in`?.nextString()
            }
        } catch (e: Exception) {
            value = ""
        }
        return value
    }
}