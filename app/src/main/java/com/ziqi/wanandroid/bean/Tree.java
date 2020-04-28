package com.ziqi.wanandroid.bean;

import java.util.List;

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2020/4/28-14:16
 * desc   :
 * version: 1.0
 */
public class Tree {

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
    public String id;
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
        public String id;
        public String name;
        public String order;
        public String parentChapterId;
        public boolean userControlSetTop;
        public String visible;
        //public List<String> children;
    }
}
