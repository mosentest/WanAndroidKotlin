package com.mo.bee.main.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.mo.bee.R;
import com.mo.bee.xfloatview.XFloatView;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/9/21 3:15 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class FloatView extends XFloatView {
    private ImageView mBeeIcon;

    /**
     * 构造器
     *
     * @param context
     */
    public FloatView(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bee_view_float;
    }

    @Override
    protected boolean canMoveOrTouch() {
        return true;
    }

    @Override
    protected void initFloatView() {
        mBeeIcon = findViewById(R.id.bee_icon);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected boolean isAdsorbView() {
        return true;
    }
}
