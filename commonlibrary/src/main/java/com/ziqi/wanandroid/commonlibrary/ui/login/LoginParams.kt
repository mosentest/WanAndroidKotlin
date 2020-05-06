package com.ziqi.wanandroid.commonlibrary.ui.login

import android.os.Parcel
import android.os.Parcelable

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/5 2:12 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class LoginParams() :Parcelable {

    var isInvalid = false

    constructor(parcel: Parcel) : this() {
        isInvalid = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isInvalid) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginParams> {
        override fun createFromParcel(parcel: Parcel): LoginParams {
            return LoginParams(parcel)
        }

        override fun newArray(size: Int): Array<LoginParams?> {
            return arrayOfNulls(size)
        }
    }
}