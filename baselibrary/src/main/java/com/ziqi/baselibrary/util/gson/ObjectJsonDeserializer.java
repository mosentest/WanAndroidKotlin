package com.ziqi.baselibrary.util.gson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2020/6/8-11:31
 * desc   :
 * version: 1.0
 */
public class ObjectJsonDeserializer<T> implements JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        T temp = null;
        if (json.isJsonObject()) {
            temp = GsonUtil.INSTANCE.getInnerGson().fromJson(json, typeOfT);
            return temp;
        }
        return temp;
    }
}
