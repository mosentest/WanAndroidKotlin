package com.ziqi.baselibrary.base

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ziqi.baselibrary.BuildConfig
import com.ziqi.baselibrary.base.`interface`.IBaseView
import com.ziqi.baselibrary.util.ClickUtil
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.baselibrary.util.StartActivityCompat
import com.ziqi.baselibrary.util.ToastUtil

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/3 5:45 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
abstract class BaseActivity<StartActInfo : Parcelable, Binding : ViewDataBinding> :
    AppCompatActivity(),
    View.OnClickListener,
    IBaseView {

    protected val TAG: String = BaseActivity::class.java.getSimpleName()

    protected var mIntentData: StartActInfo? = null

    protected var mViewDataBinding: Binding? = null

    protected var mTitle: String? = null

    protected var mShowBack: Boolean? = false

    init {
        /**
         * https://www.jianshu.com/p/0972a0d290e9
         */
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (zIsEventBus()) {
            //增加eventbus
        }
        if (zIsDataBinding()) {
            mViewDataBinding = DataBindingUtil.setContentView(this, zSetLayoutId())
        } else {
            setContentView(zSetLayoutId())
        }
        handleIntent(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (zIsEventBus()) {
            //移除evenbus
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        /**
         * 如果处于栈顶的话，直接走onResume
         * 不然都会走onReStart->onStart
         */
        setIntent(intent)
        handleIntent(true)
    }

    private fun handleIntent(isNewIntent: Boolean) {
        LogUtil.i(TAG, """${zGetClassName()}>>>handleIntent""")
        intent.extras?.apply {
            mTitle = this.getString(StartActivityCompat.NEXT_TITLE)
            mShowBack = this.getBoolean(StartActivityCompat.NEXT_SHOW_BACK, true)
            mIntentData = this.getParcelable(StartActivityCompat.NEXT_PARCELABLE)
        }
        zVisibleToUser(true)
    }

    override fun zGetClassName(): String {
        return javaClass.simpleName
    }

    override fun zToastLong(flag: Int, msg: String?) {
        ToastUtil.showLongToast(msg)
    }

    override fun zToastShort(flag: Int, msg: String?) {
        ToastUtil.showShortToast(msg)
    }

    override fun zConfirmDialog(flag: Int, msg: String?) {
        val dialog = AlertDialog.Builder(applicationContext)
            .setMessage(msg)
            .setTitle("温馨提示")
            .setPositiveButton("确认") { p0, p1 ->
                p0.dismiss()
            }
            .create()
        dialog.show()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        // ----------事件分发-------->-------事件处理--------->
        // activity -> 父view -> 子view -> 父view -> activity
        // 在activity的事件分发处对用户高频率点击进行判断拦截，避免用户拿app消遣
        if (ev.action == MotionEvent.ACTION_DOWN) {
            // 界面上多个控件都需要添加防抖操作
            if (!onFastDoubleClickEnable() && ClickUtil.isFastDoubleClick) {
                if (BuildConfig.DEBUG) {
                    LogUtil.i(TAG, """${zGetClassName()}>>>dispatchTouchEvent 不允许（不启用）快速点击""")
                }
                return true
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 默认值为false
     */
    protected open fun onFastDoubleClickEnable(): Boolean {
        return false
    }

    //控制startactivity
    // 参考这个哥们https://www.jianshu.com/p/579f1f118161
    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        if (BuildConfig.DEBUG) {
            LogUtil.i(TAG, """${zGetClassName()}>>>startActivityForResult -- 开始 --""")
        }
        if (startActivitySelfCheck(intent)) {
            if (BuildConfig.DEBUG) {
                LogUtil.i(TAG, """${zGetClassName()}>>>startActivityForResult -- 满足 --""")
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
            LogUtil.i(TAG, zGetClassName() + ">>>startActivitySelfCheck -- 开始 --")
        }
        // 默认检查通过
        var result = true
        if (BuildConfig.DEBUG) {
            LogUtil.i(
                TAG,
                """${zGetClassName()}>>>startActivitySelfCheck -- intent --${intent == null}"""
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
                LogUtil.i(TAG, """${zGetClassName()}>>>startActivitySelfCheck -- 显式跳转 --$tag""")
            }
        } else if (intent.action != null) { // 隐式跳转
            tag = intent.action
            if (BuildConfig.DEBUG) {
                LogUtil.i(TAG, """${zGetClassName()}>>>startActivitySelfCheck -- 隐式跳转 --$tag""")
            }
        } else {
            return result
        }
        if (tag == mActivityJumpTag && mActivityJumpTime >= SystemClock.uptimeMillis() - 500) { // 检查不通过
            result = false
            if (BuildConfig.DEBUG) {
                LogUtil.i(
                    TAG,
                    """${zGetClassName()}>>>startActivitySelfCheck -- result --$result"""
                )
            }
        }
        // 记录启动标记和时间
        mActivityJumpTag = tag
        mActivityJumpTime = SystemClock.uptimeMillis()
        return result
    }
}