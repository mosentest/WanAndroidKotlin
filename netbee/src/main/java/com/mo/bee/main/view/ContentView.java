package com.mo.bee.main.view;

import android.content.Context;

import com.mo.bee.R;
import com.mo.bee.xfloatview.XFloatView;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/9/21 3:17 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class ContentView extends XFloatView {
    /**
     * 构造器
     *
     * @param context
     */
    public ContentView(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bee_view_content;
    }

    @Override
    protected boolean canMoveOrTouch() {
        return true;
    }

    @Override
    protected void initFloatView() {
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected boolean isAdsorbView() {
        return false;
    }
}
