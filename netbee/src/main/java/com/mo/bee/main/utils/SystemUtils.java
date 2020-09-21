package com.mo.bee.main.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/9/21 5:30 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class SystemUtils {

    //复制
    public static void copy(Context context, String data) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 把数据集设置（复制）到剪贴板
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText("content", data));
        }
    }

}
