package com.ziqi.wanandroid.commonlibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/23 8:58 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class User implements Parcelable {

    /**
     * admin : false
     * chapterTops : []
     * collectIds : [13244]
     * email :
     * icon :
     * id : 25898
     * nickname : 709847739@qq.com
     * password :
     * publicName : 709847739@qq.com
     * token :
     * type : 0
     * username : 709847739@qq.com
     */

    public String admin;
    public String email;
    public String icon;
    public String id;
    public String nickname;
    public String password;
    public String publicName;
    public String token;
    public String type;
    public String username;
    public List<String> chapterTops;
    public List<String> collectIds;

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.admin);
        dest.writeString(this.email);
        dest.writeString(this.icon);
        dest.writeString(this.id);
        dest.writeString(this.nickname);
        dest.writeString(this.password);
        dest.writeString(this.publicName);
        dest.writeString(this.token);
        dest.writeString(this.type);
        dest.writeString(this.username);
        dest.writeStringList(this.chapterTops);
        dest.writeStringList(this.collectIds);
    }

    protected User(Parcel in) {
        this.admin = in.readString();
        this.email = in.readString();
        this.icon = in.readString();
        this.id = in.readString();
        this.nickname = in.readString();
        this.password = in.readString();
        this.publicName = in.readString();
        this.token = in.readString();
        this.type = in.readString();
        this.username = in.readString();
        this.chapterTops = in.createStringArrayList();
        this.collectIds = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
