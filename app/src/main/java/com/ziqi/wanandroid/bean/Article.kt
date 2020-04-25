package com.ziqi.wanandroid.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/16 12:06 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class Article() :Parcelable{
    /**
     * apkLink :
     * audit : 1
     * author : 扔物线
     * canEdit : false
     * chapterId : 249
     * chapterName : 干货资源
     * collect : false
     * courseId : 13
     * desc :
     * descMd :
     * envelopePic :
     * fresh : true
     * id : 12554
     * link : https://mp.weixin.qq.com/s/CFWznkSrq6JmW1fZdqdlOg
     * niceDate : 刚刚
     * niceShareDate : 2020-03-23 16:36
     * origin :
     * prefix :
     * projectLink :
     * publishTime : 1587657600000
     * selfVisible : 0
     * shareDate : 1584952597000
     * shareUser :
     * superChapterId : 249
     * superChapterName : 干货资源
     * tags : []
     * title : 【扔物线】消失了半年，这个 Android 界的第一骚货终于回来了
     * type : 1
     * userId : -1
     * visible : 1
     * zan : 0
     */
    var apkLink: String? = null
    var audit: String? = null
    var author: String? = null
    var canEdit: String? = null
    var chapterId: String? = null
    var chapterName: String? = null
    var collect: String? = null
    var courseId: String? = null
    var desc: String? = null
    var descMd: String? = null
    var envelopePic: String? = null
    var fresh: String? = null
    var id: String? = null
    var link: String? = null
    var niceDate: String? = null
    var niceShareDate: String? = null
    var origin: String? = null
    var prefix: String? = null
    var projectLink: String? = null
    var publishTime: String? = null
    var selfVisible: String? = null
    var shareDate: String? = null
    var shareUser: String? = null
    var superChapterId: String? = null
    var superChapterName: String? = null
    var title: String? = null
    var type: String? = null
    var userId: String? = null
    var visible: String? = null
    var zan: String? = null
    var tags: List<Tag>? = null

    constructor(parcel: Parcel) : this() {
        apkLink = parcel.readString()
        audit = parcel.readString()
        author = parcel.readString()
        canEdit = parcel.readString()
        chapterId = parcel.readString()
        chapterName = parcel.readString()
        collect = parcel.readString()
        courseId = parcel.readString()
        desc = parcel.readString()
        descMd = parcel.readString()
        envelopePic = parcel.readString()
        fresh = parcel.readString()
        id = parcel.readString()
        link = parcel.readString()
        niceDate = parcel.readString()
        niceShareDate = parcel.readString()
        origin = parcel.readString()
        prefix = parcel.readString()
        projectLink = parcel.readString()
        publishTime = parcel.readString()
        selfVisible = parcel.readString()
        shareDate = parcel.readString()
        shareUser = parcel.readString()
        superChapterId = parcel.readString()
        superChapterName = parcel.readString()
        title = parcel.readString()
        type = parcel.readString()
        userId = parcel.readString()
        visible = parcel.readString()
        zan = parcel.readString()
        tags = parcel.createTypedArrayList(Tag)
    }

    class Tag() : Parcelable {
        /**
         * name : 问答
         * url : /wenda
         */
        var name: String? = null
        var url: String? = null

        constructor(parcel: Parcel) : this() {
            name = parcel.readString()
            url = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(url)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Tag> {
            override fun createFromParcel(parcel: Parcel): Tag {
                return Tag(parcel)
            }

            override fun newArray(size: Int): Array<Tag?> {
                return arrayOfNulls(size)
            }
        }

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(apkLink)
        parcel.writeString(audit)
        parcel.writeString(author)
        parcel.writeString(canEdit)
        parcel.writeString(chapterId)
        parcel.writeString(chapterName)
        parcel.writeString(collect)
        parcel.writeString(courseId)
        parcel.writeString(desc)
        parcel.writeString(descMd)
        parcel.writeString(envelopePic)
        parcel.writeString(fresh)
        parcel.writeString(id)
        parcel.writeString(link)
        parcel.writeString(niceDate)
        parcel.writeString(niceShareDate)
        parcel.writeString(origin)
        parcel.writeString(prefix)
        parcel.writeString(projectLink)
        parcel.writeString(publishTime)
        parcel.writeString(selfVisible)
        parcel.writeString(shareDate)
        parcel.writeString(shareUser)
        parcel.writeString(superChapterId)
        parcel.writeString(superChapterName)
        parcel.writeString(title)
        parcel.writeString(type)
        parcel.writeString(userId)
        parcel.writeString(visible)
        parcel.writeString(zan)
        parcel.writeTypedList(tags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}