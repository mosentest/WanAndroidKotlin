package com.ziqi.baselibrary.base

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ziqi.baselibrary.BuildConfig
import com.ziqi.baselibrary.R
import com.ziqi.baselibrary.base.interfaces.IBaseFragment
import com.ziqi.baselibrary.base.interfaces.IBaseView
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.baselibrary.util.StartActivityCompat
import com.ziqi.baselibrary.util.ToastUtil
import com.ziqi.baselibrary.view.status.ZStatusView
import com.ziqi.baselibrary.view.status.ZStatusViewBuilder
import kotlinx.android.synthetic.main.fragment_base.*
import org.greenrobot.eventbus.EventBus

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/8 10:21 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
abstract class ZBaseFragment<StartParams : Parcelable, Binding : ViewDataBinding> :
    Fragment(),
    View.OnClickListener,
    IBaseView,
    IBaseFragment {

    protected val TAG: String = ZBaseFragment::class.java.getSimpleName()

    /**
     * 根view
     */
    protected var mRootView: View? = null

    /**
     * 上个页面带过来的数据
     */
    protected var mBundleData: StartParams? = null

    /**
     * DataBinding
     */
    protected var mViewDataBinding: Binding? = null

    /**
     * 标题
     */
    protected var mTitle: String? = null

    /**
     * 是否展示返回键
     */
    protected var mShowBack: Boolean = false

    /**
     * 状态view
     */
    private var mZStatusView: ZStatusView? = null


    /**
     * toobar控件
     */
    protected var mToolBar: Toolbar? = null

    /**
     * 标题内容
     */
    protected var mTvTitle: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (zIsEventBus()) {
            //增加eventbus
            EventBus.getDefault().register(this)
        }
        arguments?.apply {
            mTitle = getString(StartActivityCompat.NEXT_TITLE)
            mShowBack = getBoolean(StartActivityCompat.NEXT_SHOW_BACK, false)
            mBundleData = getParcelable(StartActivityCompat.NEXT_PARCELABLE)
        }
        savedInstanceState?.apply {
            mTitle = getString(StartActivityCompat.NEXT_TITLE)
            mShowBack = getBoolean(StartActivityCompat.NEXT_SHOW_BACK, false)
            mBundleData = getParcelable(StartActivityCompat.NEXT_PARCELABLE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(StartActivityCompat.NEXT_TITLE, mTitle)
        outState.putBoolean(StartActivityCompat.NEXT_SHOW_BACK, mShowBack)
        outState.putParcelable(StartActivityCompat.NEXT_PARCELABLE, mBundleData)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (zSetLayoutId() != -1) {
            mRootView = inflater.inflate(zSetLayoutId(), container, false)
            if (zIsDataBinding()) {
                mViewDataBinding = DataBindingUtil.bind(mRootView!!)
            }
            return mRootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /**
     * 默认设置这个LayoutId
     */
    override fun zSetLayoutId(): Int = R.layout.fragment_base

    /**
     * 实现一个findViewById的方法
     *
     * @param idRes
     * @param <T>
     * @return</T>
     */
    open fun <T : View> findViewById(idRes: Int): T? {
        return if (idRes == View.NO_ID) {
            null
        } else mRootView?.findViewById(idRes)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (zContentViewId() != -1) {
            mZStatusView = ZStatusView.init(this, zContentViewId(), zLoadingDialogId())
        }
    }

    /**
     * 悬浮在内容上面的加载布局
     */
    open fun zLoadingDialogId(): Int = R.layout.zsv_load_dialog_layout

    /**
     * 控制展示内容的根布局
     */
    open fun zContentViewId(): Int = -1


    /**
     * 处理数据问题
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBaseView()
        zVisibleToUser(false)
    }

    /**
     * 处理数据问题
     */
    override fun onNewIntent(bundle: Bundle?) {
        bundle?.apply {
            mTitle = getString(StartActivityCompat.NEXT_TITLE)
            mShowBack = getBoolean(StartActivityCompat.NEXT_SHOW_BACK, false)
            mBundleData = getParcelable(StartActivityCompat.NEXT_PARCELABLE)
        }
        initBaseView()
        zVisibleToUser(true)
    }

    /**
     * 处理头部的逻辑
     */
    fun initBaseView() {
        mToolBar = findViewById(R.id.toolbar);
        mTvTitle = findViewById(R.id.title);
        activity?.apply {
            (activity as AppCompatActivity).apply {
                setSupportActionBar(mToolBar)
                // 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
                supportActionBar?.setDisplayHomeAsUpEnabled(mShowBack)
                //设置actionBar的标题是否显示，对应ActionBar.DISPLAY_SHOW_TITLE。
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }
        }
        mTvTitle?.text = mTitle
    }

    override fun onDestroy() {
        super.onDestroy()
        if (zIsEventBus()) {
            //移除evenbus
            EventBus.getDefault().unregister(this)
        }
    }

    override fun zToastLong(flag: Int, msg: String?) {
        ToastUtil.showLongToast(msg)
    }

    override fun zToastShort(flag: Int, msg: String?) {
        ToastUtil.showShortToast(msg)
    }

    override fun zConfirmDialog(flag: Int, msg: String?) {
        context?.apply {
            val dialog = AlertDialog.Builder(this)
                .setMessage(msg)
                .setTitle("温馨提示")
                .setPositiveButton("确认") { p0, p1 ->
                    p0.dismiss()
                }
                .create()
            dialog.show()
        }
    }

    override fun zHideLoadingDialog(flag: Int) {
        mZStatusView?.hideLoadDialog()
    }

    override fun zShowLoadingDialog(flag: Int, msg: String?) {
        mZStatusView?.showLoadDialog()
    }

    override fun zStatusContentView() {
        mZStatusView?.showContentView()
    }

    override fun zStatusErrorView(type: Int, msg: String?) {
        mZStatusView?.showErrorView()
    }

    override fun zStatusLoadingView() {
        mZStatusView?.showLoadingView()
    }

    override fun zStatusEmptyView() {
        mZStatusView?.showEmptyView()
    }

    override fun zGetClassName(): String {
        return javaClass.simpleName
    }


    override fun startActivity(intent: Intent?, options: Bundle?) {
        if (BuildConfig.DEBUG) {
            LogUtil.i(
                TAG,
                zGetClassName() + ">>>startActivity options -- 开始 --"
            )
        }
        if (startActivitySelfCheck(intent)) {
            if (BuildConfig.DEBUG) {
                LogUtil.i(
                    TAG,
                    zGetClassName() + ">>>startActivity options -- 满足 --"
                )
            }
            // 查看源码得知 startActivity 最终也会调用 startActivityForResult
            super.startActivity(intent, options)
        }
    }

    //控制startactivity 参考这个哥们https://www.jianshu.com/p/579f1f118161
    override fun startActivityForResult(
        intent: Intent?,
        requestCode: Int,
        options: Bundle?
    ) {
        if (BuildConfig.DEBUG) {
            LogUtil.i(
                TAG,
                zGetClassName() + ">>>startActivityForResult -- 开始 --"
            )
        }
        if (startActivitySelfCheck(intent)) {
            if (BuildConfig.DEBUG) {
                LogUtil.i(
                    TAG,
                    zGetClassName() + ">>>startActivityForResult -- 满足 --"
                )
            }
            // 查看源码得知 startActivity 最终也会调用 startActivityForResult
            super.startActivityForResult(intent, requestCode, options)
        }
    }

    private var mActivityJumpTag: String? = null

    private var mActivityJumpTime: Long = 0

    /**
     * 检查当前 Activity 是否重复跳转了，不需要检查则重写此方法并返回 true 即可
     *
     * @param intent 用于跳转的 Intent 对象
     * @return 检查通过返回true, 检查不通过返回false
     */
    protected open fun startActivitySelfCheck(intent: Intent?): Boolean {
        if (BuildConfig.DEBUG) {
            LogUtil.i(
                TAG,
                zGetClassName() + ">>>startActivitySelfCheck -- 开始 --"
            )
        }
        // 默认检查通过
        var result = true
        if (BuildConfig.DEBUG) {
            LogUtil.i(
                TAG,
                zGetClassName() + ">>>startActivitySelfCheck -- intent --" + (intent == null)
            )
        }
        if (intent == null) {
            return result
        }
        // 标记对象
        val tag: String?
        if (intent.component != null) { // 显式跳转
            tag = intent.component!!.className
            if (BuildConfig.DEBUG) {
                LogUtil.i(
                    TAG,
                    zGetClassName() + ">>>startActivitySelfCheck -- 显式跳转 --" + tag
                )
            }
        } else if (intent.action != null) { // 隐式跳转
            tag = intent.action
            if (BuildConfig.DEBUG) {
                LogUtil.i(
                    TAG,
                    zGetClassName() + ">>>startActivitySelfCheck -- 隐式跳转 --" + tag
                )
            }
        } else {
            return result
        }
        if (tag == mActivityJumpTag && mActivityJumpTime >= SystemClock.uptimeMillis() - 500) { // 检查不通过
            result = false
            if (BuildConfig.DEBUG) {
                LogUtil.i(
                    TAG,
                    zGetClassName() + ">>>startActivitySelfCheck -- result --" + result
                )
            }
        }
        // 记录启动标记和时间
        mActivityJumpTag = tag
        mActivityJumpTime = SystemClock.uptimeMillis()
        return result
    }
}