package com.ziqi.baselibrary;

import android.os.Parcel;
import android.os.Parcelable;

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
 */
public class MyTime implements Parcelable {

    private String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public MyTime() {
    }

    protected MyTime(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        this.name = in.readString();
    }

    public static final Creator<MyTime> CREATOR = new Creator<MyTime>() {
        @Override
        public MyTime createFromParcel(Parcel source) {
            return new MyTime(source);
        }

        @Override
        public MyTime[] newArray(int size) {
            return new MyTime[size];
        }
    };
}
