package com.ziqi.baselibrary.util.gson

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class DoubleAdapter : TypeAdapter<Double?>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter?, value: Double?) {
        try {
            var tempValue = value
            if (tempValue == null) {
                tempValue = -1.0
            }
            out?.value(tempValue)
        } catch (e: Exception) {

        }
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Double? {
        var value: Double? = -1.0
        try {
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                value = -1.0
            } else if (`in`.peek() == JsonToken.BOOLEAN) {
                val b = `in`.nextBoolean()
                value = if (b) 1.0 else 0.0
            } else if (`in`.peek() == JsonToken.STRING) {
                try {
                    value = `in`.nextString().toDouble()
                } catch (e: Exception) {
                    value = -1.0
                }
            } else {
                value = `in`.nextDouble()
            }
        } catch (e: Exception) {
            value = -1.0
        }
        return value
    }
}