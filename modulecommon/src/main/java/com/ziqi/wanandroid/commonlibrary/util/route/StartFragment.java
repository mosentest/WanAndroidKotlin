package com.ziqi.wanandroid.commonlibrary.util.route;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2020/5/6-11:16
 * desc   :
 * version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface StartFragment {
    String value();
}
