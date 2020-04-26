package com.ziqi.baselibrary

import android.os.Parcel
import android.os.Parcelable

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/3 10:13 PM
 * Description:
 * History:
 * 参考资料
 * https://github.com/leavesC/IPCSamples
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class MyTime() : Parcelable {

    var name: String? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
    }

    fun readFromParcel(parcel: Parcel) {
        name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyTime> {
        override fun createFromParcel(parcel: Parcel): MyTime {
            return MyTime(parcel)
        }

        override fun newArray(size: Int): Array<MyTime?> {
            return arrayOfNulls(size)
        }
    }

}
