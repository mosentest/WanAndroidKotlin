package com.ziqi.wanandroid.bean

import android.R
import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/15 11:06 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class Banner() : Parcelable {
    /**
     * desc : 享学~
     * id : 29
     * imagePath : https://wanandroid.com/blogimgs/6f9c0f25-e02d-48a5-bbfa-ed199416009a.png
     * isVisible : 1
     * order : 0
     * title : 产品上线，突发bug
     * type : 0
     * url : https://mp.weixin.qq.com/s/TcF4w0Bz-k5dZdPKq3HIGA
     */
    var desc: String? = null
    var id: String? = null
    var imagePath: String? = null
    var isVisible: String? = null
    var order: String? = null
    var title: String? = null
    var type: String? = null
    var url: String? = null

    constructor(parcel: Parcel) : this() {
        desc = parcel.readString()
        id = parcel.readString()
        imagePath = parcel.readString()
        isVisible = parcel.readString()
        order = parcel.readString()
        title = parcel.readString()
        type = parcel.readString()
        url = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(desc)
        parcel.writeString(id)
        parcel.writeString(imagePath)
        parcel.writeString(isVisible)
        parcel.writeString(order)
        parcel.writeString(title)
        parcel.writeString(type)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Banner> {
        override fun createFromParcel(parcel: Parcel): Banner {
            return Banner(parcel)
        }

        override fun newArray(size: Int): Array<Banner?> {
            return arrayOfNulls(size)
        }
    }
}