package com.ziqi.wanandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2020/4/28-14:16
 * desc   :
 * version: 1.0
 */
public class Tree implements Parcelable {

    /**
     * children : [{"children":[],"courseId":13,"id":60,"name":"Android Studio相关","order":1000,"parentChapterId":150,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":169,"name":"gradle","order":1001,"parentChapterId":150,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":269,"name":"官方发布","order":1002,"parentChapterId":150,"userControlSetTop":false,"visible":1}]
     * courseId : 13
     * id : 150
     * name : 开发环境
     * order : 1
     * parentChapterId : 0
     * userControlSetTop : false
     * visible : 1
     */

    public String courseId;
    public int id;
    public String name;
    public String order;
    public String parentChapterId;
    public boolean userControlSetTop;
    public String visible;
    public List<ChildrenBean> children;

    public static class ChildrenBean {
        /**
         * children : []
         * courseId : 13
         * id : 60
         * name : Android Studio相关
         * order : 1000
         * parentChapterId : 150
         * userControlSetTop : false
         * visible : 1
         */

        public String courseId;
        public int id;
        public String name;
        public String order;
        public String parentChapterId;
        public boolean userControlSetTop;
        public String visible;
        //public List<String> children;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.courseId);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.order);
        dest.writeString(this.parentChapterId);
        dest.writeByte(this.userControlSetTop ? (byte) 1 : (byte) 0);
        dest.writeString(this.visible);
        dest.writeList(this.children);
    }

    public Tree() {
    }

    protected Tree(Parcel in) {
        this.courseId = in.readString();
        this.id = in.readInt();
        this.name = in.readString();
        this.order = in.readString();
        this.parentChapterId = in.readString();
        this.userControlSetTop = in.readByte() != 0;
        this.visible = in.readString();
        this.children = new ArrayList<ChildrenBean>();
        in.readList(this.children, ChildrenBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Tree> CREATOR = new Parcelable.Creator<Tree>() {
        @Override
        public Tree createFromParcel(Parcel source) {
            return new Tree(source);
        }

        @Override
        public Tree[] newArray(int size) {
            return new Tree[size];
        }
    };
}
