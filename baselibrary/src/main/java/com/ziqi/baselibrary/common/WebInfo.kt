package com.ziqi.baselibrary.common

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/10 2:12 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
//@Parcelize
class WebInfo() : Parcelable {
    var url: String? = null
    var title: String? = null

    constructor(parcel: Parcel) : this() {
        url = parcel.readString()
        title = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WebInfo> {
        override fun createFromParcel(parcel: Parcel): WebInfo {
            return WebInfo(parcel)
        }

        override fun newArray(size: Int): Array<WebInfo?> {
            return arrayOfNulls(size)
        }
    }


}