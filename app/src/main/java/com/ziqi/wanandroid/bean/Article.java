package com.ziqi.wanandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/16 12:06 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class Article implements Parcelable {

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

    public String apkLink;
    public String audit;
    public String author;
    public String canEdit;
    public String chapterId;
    public String chapterName;
    public String collect;
    public String courseId;
    public String desc;
    public String descMd;
    public String envelopePic;
    public String fresh;
    public String id;
    public String link;
    public String niceDate;
    public String niceShareDate;
    public String origin;
    public String prefix;
    public String projectLink;
    public String publishTime;
    public String selfVisible;
    public String shareDate;
    public String shareUser;
    public String superChapterId;
    public String superChapterName;
    public String title;
    public String type;
    public String userId;
    public String visible;
    public String zan;
    public List<Tag> tags;

    static class Tag {
        /**
         * name : 问答
         * url : /wenda
         */

        public String name;
        public String url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.apkLink);
        dest.writeString(this.audit);
        dest.writeString(this.author);
        dest.writeString(this.canEdit);
        dest.writeString(this.chapterId);
        dest.writeString(this.chapterName);
        dest.writeString(this.collect);
        dest.writeString(this.courseId);
        dest.writeString(this.desc);
        dest.writeString(this.descMd);
        dest.writeString(this.envelopePic);
        dest.writeString(this.fresh);
        dest.writeString(this.id);
        dest.writeString(this.link);
        dest.writeString(this.niceDate);
        dest.writeString(this.niceShareDate);
        dest.writeString(this.origin);
        dest.writeString(this.prefix);
        dest.writeString(this.projectLink);
        dest.writeString(this.publishTime);
        dest.writeString(this.selfVisible);
        dest.writeString(this.shareDate);
        dest.writeString(this.shareUser);
        dest.writeString(this.superChapterId);
        dest.writeString(this.superChapterName);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeString(this.userId);
        dest.writeString(this.visible);
        dest.writeString(this.zan);
        dest.writeList(this.tags);
    }

    public Article() {
    }

    protected Article(Parcel in) {
        this.apkLink = in.readString();
        this.audit = in.readString();
        this.author = in.readString();
        this.canEdit = in.readString();
        this.chapterId = in.readString();
        this.chapterName = in.readString();
        this.collect = in.readString();
        this.courseId = in.readString();
        this.desc = in.readString();
        this.descMd = in.readString();
        this.envelopePic = in.readString();
        this.fresh = in.readString();
        this.id = in.readString();
        this.link = in.readString();
        this.niceDate = in.readString();
        this.niceShareDate = in.readString();
        this.origin = in.readString();
        this.prefix = in.readString();
        this.projectLink = in.readString();
        this.publishTime = in.readString();
        this.selfVisible = in.readString();
        this.shareDate = in.readString();
        this.shareUser = in.readString();
        this.superChapterId = in.readString();
        this.superChapterName = in.readString();
        this.title = in.readString();
        this.type = in.readString();
        this.userId = in.readString();
        this.visible = in.readString();
        this.zan = in.readString();
        this.tags = new ArrayList<Tag>();
        in.readList(this.tags, Tag.class.getClassLoader());
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
