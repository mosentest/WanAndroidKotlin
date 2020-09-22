package com.mo.bee.main.log;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mo.bee.R;
import com.mo.bee.main.adapter.ContentAdapter;
import com.mo.bee.main.interceptor.BeeHttpLoggingInterceptor;
import com.mo.bee.main.utils.SystemUtils;
import com.mo.bee.main.view.ContentView;
import com.mo.bee.main.view.FloatView;
import com.mo.bee.xfloatview.permission.FloatWindowPermission;

import java.util.ArrayList;
import java.util.List;


/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/9/21 3:04 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class BeeLog implements BeeHttpLoggingInterceptor.Logger {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private FloatView mFloatView;

    private ContentView mContentView;

    private List<String> mContents = new ArrayList<>();

    private ContentAdapter mBaseAdapter = null;

    private ListView beeListView = null;

    private Context context;

    private int count = 0;

    public BeeLog(Context context) {
        this.context = context;
    }

    public abstract void printLog(String message) throws RuntimeException;

    @Override
    public void log(String message) {
        try {
            printLog(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mContents.add(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean checkPermission = FloatWindowPermission.getInstance().checkPermission(context);
        if (!checkPermission) {
            FloatWindowPermission.commonROMPermissionApplyInternal(context);
            return;
        }
        if (mFloatView == null) {
            //切到主线程
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mFloatView != null) {
                            return;
                        }
                        mFloatView = new FloatView(context);
                        mFloatView.show();
                        mFloatView.setOnFloatViewClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mFloatView != null) {
                                    mFloatView.dismiss();
                                }
                                if (mContentView != null) {
                                    mContentView.show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (mContentView == null) {
            //切到主线程
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mContentView != null) {
                            return;
                        }
                        mContentView = new ContentView(context);
                        mContentView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mFloatView != null) {
                                    mFloatView.show();
                                }
                                if (mContentView != null) {
                                    mContentView.dismiss();
                                }
                            }
                        });
                        mContentView.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    mContents.clear();
                                    if (mBaseAdapter != null) {
                                        mBaseAdapter.setDatas(mContents);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        beeListView = mContentView.findViewById(R.id.beeList);
                        mBaseAdapter = new ContentAdapter(context);
                        beeListView.setAdapter(mBaseAdapter);

                        beeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                String msg = mBaseAdapter.getDatas().get(position);
                                SystemUtils.copy(context, msg);
                                Toast.makeText(context, "复制成功！", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        try {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //不断刷新数据到列表里面进去
                    if (mBaseAdapter != null) {
                        mBaseAdapter.setDatas(mContents);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
