package com.ziqi.baselibrary.base

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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

    var mRootView: View? = null

    protected val TAG: String = ZBaseFragment::class.java.getSimpleName()

    protected var mBundleData: StartParams? = null

    protected var mViewDataBinding: Binding? = null

    protected var mTitle: String? = null

    protected var mShowBack: Boolean = false


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


    override fun zSetLayoutId(): Int {
        return R.layout.fragment_base
    }
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        zVisibleToUser(false)
    }

    override fun onNewIntent(bundle: Bundle?) {
        bundle?.apply {
            mTitle = getString(StartActivityCompat.NEXT_TITLE)
            mShowBack = getBoolean(StartActivityCompat.NEXT_SHOW_BACK, false)
            mBundleData = getParcelable(StartActivityCompat.NEXT_PARCELABLE)
        }
        zVisibleToUser(true)
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

    }

    override fun zShowLoadingDialog(flag: Int, msg: String?) {

    }

    override fun zStatusContentView() {

    }

    override fun zStatusErrorView(type: Int, msg: String?) {

    }

    override fun zStatusLoadingView() {

    }

    override fun zStatusNetWorkView() {

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