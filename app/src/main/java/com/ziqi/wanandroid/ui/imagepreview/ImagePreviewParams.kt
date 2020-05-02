package com.ziqi.wanandroid.ui.imagepreview

import android.os.Parcel
import android.os.Parcelable

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/1 7:54 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class ImagePreviewParams() : Parcelable {

    var imgUrl: List<String>? = null

    var currentPos: Int = 0




    constructor(parcel: Parcel) : this() {
        imgUrl = parcel.createStringArrayList()
        currentPos = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(imgUrl)
        parcel.writeInt(currentPos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImagePreviewParams> {
        override fun createFromParcel(parcel: Parcel): ImagePreviewParams {
            return ImagePreviewParams(parcel)
        }

        override fun newArray(size: Int): Array<ImagePreviewParams?> {
            return arrayOfNulls(size)
        }
    }

}