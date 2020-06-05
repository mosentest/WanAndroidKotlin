package com.ziqi.wanandroid.me.ui.wxarticlelist

import android.os.Parcel
import android.os.Parcelable
import com.ziqi.wanandroid.commonlibrary.bean.Tree

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/30 2:50 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class WxArticleListParams() : Parcelable {

    var tree: Tree? = null

    constructor(parcel: Parcel) : this() {
        tree = parcel.readParcelable(Tree::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(tree, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WxArticleListParams> {
        override fun createFromParcel(parcel: Parcel): WxArticleListParams {
            return WxArticleListParams(parcel)
        }

        override fun newArray(size: Int): Array<WxArticleListParams?> {
            return arrayOfNulls(size)
        }
    }


}