package com.ziqi.baselibrary.view.status;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ziqi.baselibrary.R;

/**
 * https://github.com/SheHuan/StatusView
 */
public class ZStatusView extends FrameLayout {
    private Context context;

    // 当前显示的 View
    private View currentView;
    // 原始内容 View
    private View contentView;

    // 状态布局文件 Id 声明
    private @LayoutRes
    int loadingLayoutId = R.layout.zsv_loading_layout;
    private @LayoutRes
    int emptyLayoutId = R.layout.zsv_empty_layout;
    private @LayoutRes
    int errorLayoutId = R.layout.zsv_error_layout;

    private @LayoutRes
    int loadDialogLayoutRes = R.layout.zsv_load_dialog_layout;

    // 状态布局 View 缓存集合
    private SparseArray<View> viewArray = new SparseArray<>();
    // 状态布局 View 显示时的回调接口集合
    private SparseArray<ZStatusViewConvertListener> listenerArray = new SparseArray<>();
    // 索引对应的状态布局 Id 集合
    private SparseIntArray layoutIdArray = new SparseIntArray();

    // 默认状态布局文件属性配置
    private ZStatusViewBuilder builder;

    public ZStatusView(@NonNull Context context) {
        this(context, null);
    }

    public ZStatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZStatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ZStatusView, 0, 0);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.ZStatusView_zsv_loading_view) {
                loadingLayoutId = ta.getResourceId(attr, loadingLayoutId);
            } else if (attr == R.styleable.ZStatusView_zsv_empty_view) {
                emptyLayoutId = ta.getResourceId(attr, emptyLayoutId);
            } else if (attr == R.styleable.ZStatusView_zsv_error_view) {
                errorLayoutId = ta.getResourceId(attr, errorLayoutId);
            }
        }
        ta.recycle();
    }

    /**
     * 在 XML 中使用时，布局文件加载完后获得 StatusView 对应的子 ContentView
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 1) {
            View view = getChildAt(0);
            setContentView(view);
        }
    }

    /**
     * 在 Activity 中的初始化方法，默认页面的根布局使用多状态布局
     *
     * @param activity
     * @return
     */
    public static ZStatusView init(Activity activity) {
        View contentView = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        return init(contentView);
    }

    /**
     * 在 Activity 中的初始化方法
     *
     * @param activity
     * @param viewId   使用多状态布局的 ViewId
     * @return
     */
    public static ZStatusView init(Activity activity, @IdRes int viewId) {
        View rootView = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        View contentView = rootView.findViewById(viewId);
        return init(contentView);
    }

    /**
     * 在Fragment中的初始化方法
     *
     * @param fragment
     * @param viewId   使用多状态布局的 ViewId
     * @return
     */
    public static ZStatusView init(Fragment fragment, @IdRes int viewId) {
        View rootView = fragment.getView();
        View contentView = null;
        if (rootView != null) {
            contentView = rootView.findViewById(viewId);
        }
        return init(contentView);
    }

    /**
     * 在Fragment中的初始化方法
     *
     * @param fragment
     * @param viewId   使用多状态布局的 ViewId
     * @return
     */
    public static ZStatusView init(Fragment fragment, @IdRes int viewId, @LayoutRes int loadDialogLayoutRes) {
        View rootView = fragment.getView();
        View contentView = null;
        if (rootView != null) {
            contentView = rootView.findViewById(viewId);
        }
        return init(contentView, loadDialogLayoutRes);
    }

    /**
     * 用 StatusView 替换要使用多状态布局的 View
     */
    private static ZStatusView init(View contentView) {
        if (contentView == null) {
            throw new RuntimeException("ContentView can not be null!");
        }
        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent == null) {
            throw new RuntimeException("ContentView must have a parent view!");
        }
        ViewGroup.LayoutParams lp = contentView.getLayoutParams();
        int index = parent.indexOfChild(contentView);
        parent.removeView(contentView);
        ZStatusView statusView = new ZStatusView(contentView.getContext());
        statusView.addView(contentView);
        statusView.setContentView(contentView);
        parent.addView(statusView, index, lp);
        return statusView;
    }

    /**
     * 用 StatusView 替换要使用多状态布局的 View
     */
    private static ZStatusView init(View contentView, @LayoutRes int loadDialogLayoutRes) {
        if (contentView == null) {
            throw new RuntimeException("ContentView can not be null!");
        }
        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent == null) {
            throw new RuntimeException("ContentView must have a parent view!");
        }
        ViewGroup.LayoutParams lp = contentView.getLayoutParams();
        int index = parent.indexOfChild(contentView);
        parent.removeView(contentView);
        ZStatusView statusView = new ZStatusView(contentView.getContext());
        statusView.setLoadDialogView(loadDialogLayoutRes);
        /**
         * 2020-4-9
         * 这我自己新增的
         * start
         */
        FrameLayout loadFrameLayout = new FrameLayout(contentView.getContext());
        loadFrameLayout.addView(contentView);
        loadFrameLayout.addView(statusView.getLoadDialogView());
        /**
         * 2020-4-9
         * 这我自己新增的
         * end
         */
        statusView.addView(loadFrameLayout);
        statusView.setContentView(loadFrameLayout);
        parent.addView(statusView, index, lp);
        return statusView;
    }


    private View mLoadDialogView;

    /**
     * moziqi 设置自定义 LoadDialog 布局文件
     */
    private void setLoadDialogView(@LayoutRes int loadDialogLayoutRes) {
        this.loadingLayoutId = loadDialogLayoutRes;
        mLoadDialogView = inflate(loadingLayoutId);
        mLoadDialogView.setOnClickListener(view -> {

        });
        hideLoadDialog();
    }

    private View getLoadDialogView() {
        return mLoadDialogView;
    }

    /**
     * 展示dialog
     */
    public void showLoadDialog() {
        if (mLoadDialogView != null) {
            mLoadDialogView.setVisibility(VISIBLE);
            mLoadDialogView.findViewById(R.id.sv_load_dialog_progressbar);
        }
    }

    /**
     * 隐藏dialog
     */
    public void hideLoadDialog() {
        if (mLoadDialogView != null) {
            mLoadDialogView.setVisibility(GONE);
            mLoadDialogView.findViewById(R.id.sv_load_dialog_progressbar);
        }
    }


    private void setContentView(View contentView) {
        this.contentView = currentView = contentView;
    }

    /**
     * 设置自定义 Loading 布局文件
     */
    public void setLoadingView(@LayoutRes int loadingLayoutRes) {
        this.loadingLayoutId = loadingLayoutRes;
    }

    /**
     * 设置自定义 Empty 布局文件
     */
    public void setEmptyView(@LayoutRes int emptyLayoutRes) {
        this.emptyLayoutId = emptyLayoutRes;
    }

    /**
     * 设置自定义 Error 布局文件
     */
    public void setErrorView(@LayoutRes int errorLayoutRes) {
        this.errorLayoutId = errorLayoutRes;
    }

    /**
     * 显示 原始内容 布局
     */
    public void showContentView() {
        switchStatusView(contentView);
    }

    /**
     * 显示 Loading 布局
     */
    public void showLoadingView() {
        switchStatusView(loadingLayoutId);
    }

    /**
     * 显示 Empty 布局
     */
    public void showEmptyView() {
        switchStatusView(emptyLayoutId);
    }

    /**
     * 显示 Error 布局
     */
    public void showErrorView() {
        switchStatusView(errorLayoutId);
    }

    /**
     * 设置 Loading 布局首次显示时的回调，可在回调中更新布局、绑定事件等
     */
    public void setOnLoadingViewConvertListener(ZStatusViewConvertListener listener) {
        listenerArray.put(loadingLayoutId, listener);
    }

    /**
     * 设置 Empty 布局首次显示时的回调，可在回调中更新布局、绑定事件等
     */
    public void setOnEmptyViewConvertListener(ZStatusViewConvertListener listener) {
        listenerArray.put(emptyLayoutId, listener);
    }

    /**
     * 设置 Error 布局首次显示时的回调，可在回调中更新布局、绑定事件等
     */
    public void setOnErrorViewConvertListener(ZStatusViewConvertListener listener) {
        listenerArray.put(errorLayoutId, listener);
    }

    /**
     * 设置索引对应的状态布局
     *
     * @param index    布局索引
     * @param layoutId 布局 Id
     */
    public void setStatusView(int index, @LayoutRes int layoutId) {
        layoutIdArray.put(index, layoutId);
    }

    /**
     * 显示指定索引对应的状态布局
     *
     * @param index 布局索引
     */
    public void showStatusView(int index) {
        switchStatusView(layoutIdArray.get(index));
    }

    /**
     * 为指定索引对应的状态布局设置初次显示的监听事件，用来进行状态布局的相关初始化
     *
     * @param index    布局索引
     * @param listener
     */
    public void setOnStatusViewConvertListener(int index, ZStatusViewConvertListener listener) {
        listenerArray.put(layoutIdArray.get(index), listener);
    }

    /**
     * 设置默认状态布局相关控件属性
     *
     * @param builder
     */
    public void config(ZStatusViewBuilder builder) {
        this.builder = builder;
    }

    private void configStatusView(@LayoutRes int layoutId, View statusView) {
        ZViewHolder viewHolder;
        ZStatusViewConvertListener listener = listenerArray.get(layoutId);

        viewHolder = ZViewHolder.create(statusView);
        updateStatusView(layoutId, viewHolder);

        // 设置状态布局首次显示的监听接口
        if (listener != null) {
            listener.onConvert(viewHolder);
        }
    }

    private void switchStatusView(View statusView) {
        if (statusView == currentView) {
            return;
        }
        removeView(currentView);
        currentView = statusView;
        addView(currentView);
    }

    private void switchStatusView(@LayoutRes int layoutId) {
        View statusView = generateStatusView(layoutId);
        switchStatusView(statusView);
    }

    /**
     * 根据布局文件 Id 得到对应的 View，并设置控件属性、绑定接口
     */
    private View generateStatusView(@LayoutRes int layoutId) {
        View statusView = viewArray.get(layoutId);
        if (statusView == null) {
            statusView = inflate(layoutId);
            viewArray.put(layoutId, statusView);
            configStatusView(layoutId, statusView);
        }
        return statusView;
    }

    /**
     * 配置状态布局相关控件
     *
     * @param layoutId
     * @param viewHolder
     */
    private void updateStatusView(@LayoutRes int layoutId, ZViewHolder viewHolder) {
        if (builder == null) {
            return;
        }

        if (layoutId == R.layout.zsv_loading_layout) {
            setTip(R.id.sv_loading_tip, builder.getLoadingTip(), viewHolder);
            setTipColor(R.id.sv_loading_tip, viewHolder);
            setTipSize(R.id.sv_loading_tip, viewHolder);

        } else if (layoutId == R.layout.zsv_empty_layout) {
            setTip(R.id.sv_empty_tip, builder.getEmptyTip(), viewHolder);
            setTipColor(R.id.sv_empty_tip, viewHolder);
            setTipSize(R.id.sv_empty_tip, viewHolder);
            setIcon(R.id.sv_empty_icon, builder.getEmptyIcon(), viewHolder);

            setRetry(R.id.sv_empty_retry, builder.isShowEmptyRetry(), builder.getEmptyRetryText(),
                    builder.getEmptyRetryClickListener(), viewHolder);

        } else if (layoutId == R.layout.zsv_error_layout) {
            setTip(R.id.sv_error_tip, builder.getErrorTip(), viewHolder);
            setTipColor(R.id.sv_error_tip, viewHolder);
            setTipSize(R.id.sv_error_tip, viewHolder);
            setIcon(R.id.sv_error_icon, builder.getErrorIcon(), viewHolder);

            setRetry(R.id.sv_error_retry, builder.isShowErrorRetry(), builder.getErrorRetryText(),
                    builder.getErrorRetryClickListener(), viewHolder);
        }
    }

    private void setTip(int viewId, String tip, ZViewHolder viewHolder) {
        if (!TextUtils.isEmpty(tip))
            viewHolder.setText(viewId, tip);
    }

    private void setTipColor(int viewId, ZViewHolder viewHolder) {
        if (builder.getTipColor() > 0)
            viewHolder.setTextColor(viewId, getResources().getColor(builder.getTipColor()));
    }

    private void setTipSize(int viewId, ZViewHolder viewHolder) {
        if (builder.getTipSize() > 0)
            viewHolder.setTextSize(viewId, builder.getTipSize());
    }

    private void setIcon(int viewId, int iconId, ZViewHolder viewHolder) {
        if (iconId > 0)
            viewHolder.setImageResource(viewId, iconId);
    }

    public void setRetry(int viewId, boolean isShowRetry, String retryText,
                         View.OnClickListener listener, ZViewHolder viewHolder) {
        if (isShowRetry) {
            if (!TextUtils.isEmpty(retryText))
                viewHolder.setText(viewId, retryText);

            if (listener != null)
                viewHolder.setOnClickListener(viewId, listener);

            if (builder.getRetryDrawable() > 0)
                viewHolder.setBackgroundDrawable(viewId, getResources().getDrawable(builder.getRetryDrawable()));

            if (builder.getRetryColor() > 0)
                viewHolder.setTextColor(viewId, builder.getRetryColor());

            if (builder.getRetrySize() > 0)
                viewHolder.setTextSize(viewId, builder.getRetrySize());
        } else {
            viewHolder.getView(viewId).setVisibility(GONE);
        }
    }

    private View inflate(int layoutId) {
        return LayoutInflater.from(context).inflate(layoutId, null);
    }
}