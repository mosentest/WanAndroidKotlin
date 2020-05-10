package com.ziqi.baselibrary.util.statusbar.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.ziqi.baselibrary.util.statusbar.StatusBarUtil;

/**
 * 适配透明状态栏的情况
 * https://blog.csdn.net/u014418171/article/details/81223681
 */
public class StatusBarHeightLinearLayout extends LinearLayout {

    private int statusBarHeight;

    public StatusBarHeightLinearLayout(Context context) {
        super(context);
        init();
    }

    public StatusBarHeightLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusBarHeightLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StatusBarHeightLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBarHeight = StatusBarUtil.getStatusBarHeight(getContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (getContext() instanceof Activity) {
                    boolean inMultiWindowMode = ((Activity) getContext()).isInMultiWindowMode();
                    if (inMultiWindowMode) {
                        statusBarHeight = 0;
                    }
                }
            }
        } else {
            //低版本 直接设置0
            statusBarHeight = 0;
        }
        setPadding(getPaddingLeft(), getPaddingTop() + statusBarHeight, getPaddingRight(), getPaddingBottom());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}