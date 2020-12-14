package com.mo.bee.main.utils;

import android.content.Context;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 作者 create by moziqi on 2018/8/27
 * 邮箱 709847739@qq.com
 * 说明 展示类的广告，日展示的控制量
 **/
public class DisCUtil {

    /**
     * 针对单个广告,获取当日展示量
     *
     * @param type
     * @return
     */
    public static int getCurrentTypeCount(Context context, int type) {
        String count = (String) SPUtil.get(context, "dis_" + type, getResult(0));
        return parseResult(count);
    }

    /**
     * 针对单个广告,保存当日展示量
     *
     * @param type
     */
    public static void putCurrentTypeCount(Context context, int type) {
        int typeCount = getCurrentTypeCount(context, type);
        SPUtil.put(context, "dis_" + type, getResult(typeCount + 1));
    }

    private static String getResult(int count) {
        return getDay() + "-" + count;
    }

    private static int parseResult(String result) {
        if (TextUtils.isEmpty(result)) {
            return 0;
        }
        boolean contains = result.contains("-");
        if (!contains) {
            return 0;
        }
        String[] split = result.split("-");
        if (split != null && split.length == 2) {
            String day = getDay();
            String date = split[0];
            //日期一样的
            if (day.equals(date)) {
                try {
                    return Integer.parseInt(split[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }


    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    /**
     * 得到现在天
     *
     * @return
     */
    public static String getDay() {
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(8, 10);
        return min;
    }
}
