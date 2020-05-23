package com.ziqi.wanandroid.commonlibrary.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/23 7:28 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class LinearLayoutManagerX extends LinearLayoutManager {
    public LinearLayoutManagerX(Context context) {
        super(context);
    }

    public LinearLayoutManagerX(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManagerX(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * https://juejin.im/post/5eae33a26fb9a043586c7f19
     * http://frogermcs.github.io/recyclerview-animations-androiddevsummit-write-up/
     *
     * @return
     */
    @Override
    public boolean supportsPredictiveItemAnimations() {
        return true;
    }
}
