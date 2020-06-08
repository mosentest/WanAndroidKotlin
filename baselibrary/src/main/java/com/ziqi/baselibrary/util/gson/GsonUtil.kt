package com.ziqi.baselibrary.util.gson

import com.google.gson.*


/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/11 9:49 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object GsonUtil {

    val gson: Gson = GsonBuilder() //配置你的Gson
        .setDateFormat("yyyy-MM-dd hh:mm:ss")
        //https://blog.csdn.net/axxbc123/article/details/84625539
        .enableComplexMapKeySerialization()
        //https://blog.csdn.net/u010502101/article/details/80555558
        .serializeNulls()
        .registerTypeAdapter(Int::class.java, IntAdapter())
        .registerTypeAdapter(Integer::class.java, IntAdapter())
        .registerTypeAdapter(String::class.java, StringAdapter())
        .registerTypeAdapter(Double::class.java, DoubleAdapter())
        .registerTypeAdapter(Long::class.java, LongAdapter())
        .registerTypeAdapter(List::class.java, ListJsonDeserializer())
        .registerTypeAdapter(TestData.A::class.java, ObjectJsonDeserializer<TestData.A>())
        .create()

    val innerGson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd hh:mm:ss")
        .enableComplexMapKeySerialization()
        .serializeNulls()
        .registerTypeAdapter(Int::class.java, IntAdapter())
        .registerTypeAdapter(Integer::class.java, IntAdapter())
        .registerTypeAdapter(String::class.java, StringAdapter())
        .registerTypeAdapter(Double::class.java, DoubleAdapter())
        .registerTypeAdapter(Long::class.java, LongAdapter())
        .registerTypeAdapter(List::class.java, ListJsonDeserializer())
        .create()

    @JvmStatic
    fun main(args: Array<String>) {
        val testData = gson.fromJson(
            "{\n" +
                    "  \"tempInt\":\"\",\n" +
                    "  \"tempString\":1111,\n" +
                    "  \"tempAs\":[\n" +
                    "    {\n" +
                    "      \"tempInt\":\"\",\n" +
                    "      \"tempString\":1111\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"tempA\":{\n" +
                    "    \"tempInt\":\"\",\n" +
                    "    \"tempString\":1111\n" +
                    "  }\n" +
                    "}", TestData::class.java
        )
        System.out.println(testData.tempString + "=======" + testData.tempInt)
        System.out.println(testData.tempA?.toString() + "=======" + testData.tempA?.tempInt)
        System.out.println(testData.tempAs.toString())

    }
}