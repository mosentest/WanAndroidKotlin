package com.ziqi.wanandroid.ui.projectlist

import android.os.Parcel
import android.os.Parcelable
import com.ziqi.wanandroid.commonlibrary.bean.Tree

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/30 3:31 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class ProjectListParams() : Parcelable {

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

    companion object CREATOR : Parcelable.Creator<ProjectListParams> {
        override fun createFromParcel(parcel: Parcel): ProjectListParams {
            return ProjectListParams(parcel)
        }

        override fun newArray(size: Int): Array<ProjectListParams?> {
            return arrayOfNulls(size)
        }
    }
}