package com.ziqi.wanandroid.commonlibrary.ui.globaldialog

import android.os.Parcel
import android.os.Parcelable

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/7 9:20 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class GlobalParams() : Parcelable {

    var title: String? = null

    var content: String? = null

    var left: String? = null

    var right: String? = null

    var type: Int = -1 //1 去登录，2 去更新

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        content = parcel.readString()
        left = parcel.readString()
        right = parcel.readString()
        type = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(left)
        parcel.writeString(right)
        parcel.writeInt(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GlobalParams> {
        override fun createFromParcel(parcel: Parcel): GlobalParams {
            return GlobalParams(parcel)
        }

        override fun newArray(size: Int): Array<GlobalParams?> {
            return arrayOfNulls(size)
        }
    }
}