package com.ziqi.baselibrary.http.retrofit

import androidx.lifecycle.LiveData
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/14 5:45 PM
 * Description:
 * History:
 * https://blog.csdn.net/zhiwenyan/article/details/88353558
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val responseType: Type

        if (CallAdapter.Factory.getRawType(returnType) != LiveData::class.java) {
            throw IllegalStateException("return type must be parameterized")
        }
        val observableType =
            CallAdapter.Factory.getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = CallAdapter.Factory.getRawType(observableType)
        responseType = if (rawObservableType == Response::class.java) {
            if (observableType !is ParameterizedType) {
                throw IllegalArgumentException("Response must be parameterized")
            }
            CallAdapter.Factory.getParameterUpperBound(0, observableType)
        } else {
            observableType
        }
        return LiveDataCallAdapter<Any>(responseType)
    }


    /**
     * 请求适配器
     */
    class LiveDataCallAdapter<R>(var type: Type) : CallAdapter<R, LiveData<R>> {
        override fun adapt(call: Call<R>?): LiveData<R> {
            return object : LiveData<R>() {
                //这个作用是业务在多线程中,业务处理的线程安全问题,确保单一线程作业
                var flag = AtomicBoolean(false)

                override fun onActive() {
                    super.onActive()
                    if (flag.compareAndSet(false, true)) {
                        call?.apply {
                            enqueue(object : Callback<R> {
                                override fun onFailure(call: Call<R>?, t: Throwable?) {
                                    flag.set(true)
                                    postValue(null)
                                }

                                override fun onResponse(call: Call<R>?, response: Response<R>?) {
                                    flag.set(true)
                                    postValue(response?.body())
                                }
                            })
                        }
                    }
                }
            }
        }

        override fun responseType(): Type {
            return type
        }
    }
}