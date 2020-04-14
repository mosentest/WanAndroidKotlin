package com.ziqi.baselibrary.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
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
    /**
     * 左边菜单
     */
    protected var mLeftMenu: TextView? = null

    protected var mRightOneMenu: TextView? = null

    protected var mRightTwoMenu: TextView? = null


    private var mSnackbar: Snackbar? = null

    /**
     * ConnectivityManager
     */
    private var mConnectivityManager: ConnectivityManager? = null

    /**
     * 在 5.0 以下版本使用的 网络状态接收器
     */
    private var mNetWorkReceiver: NetworkStatusReceiver? = null

    /**
     * 在 5.0 及以上版本使用的 NetworkCallback
     */
    private var mNetworkCallback: ConnectivityManager.NetworkCallback? = null

    /**
     * 是否需要注销网络状态接收器
     */
    private var needUnregisterReceiver = false

    /**
     * wifi 是否已连接
     */
    private var wifiConnected = false

    /**
     * 移动网络是否已连接
     */
    private var mobileConnected = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
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
                mRootView?.apply {
                    mViewDataBinding = DataBindingUtil.bind(this)
                }
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
            zStatusLoadingView()
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
        mConnectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
        mLeftMenu = findViewById(R.id.leftMenu);
        mRightOneMenu = findViewById(R.id.rightOneMenu);
        mRightTwoMenu = findViewById(R.id.rightTwoMenu);
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

    override fun onStart() {
        super.onStart()
        // 监听网络
        listeningNetwork()
    }

    override fun onStop() {
        super.onStop()
        if (needUnregisterReceiver) {
            if (mNetWorkReceiver != null) {
                // 注销网络状态广播接收器
                activity?.apply {
                    unregisterReceiver(mNetWorkReceiver)
                    mNetWorkReceiver = null
                }
            }
        } else {
            // 注销 NetworkCallback
            unregisterNetworkCallback()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (zIsEventBus()) {
            //移除evenbus
            EventBus.getDefault().unregister(this)
        }
    }


    /**
     *
    ————————————————
    版权声明：本文为CSDN博主「PekingVagrant」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
    原文链接：https://blog.csdn.net/qq_33404903/article/details/87864922

     * 网络状态接收器
     */
    inner class NetworkStatusReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
                val activeInfo = mConnectivityManager?.activeNetworkInfo
                if (activeInfo != null && activeInfo.isConnected && activeInfo.isAvailable) {
                    val type = activeInfo.type
                    wifiConnected = type == ConnectivityManager.TYPE_WIFI
                    mobileConnected = type == ConnectivityManager.TYPE_MOBILE
                    if (wifiConnected) {
                        // wifi已连接
                        //tv.text = "wifi已连接—NetworkStatusReceiver"
                    } else if (mobileConnected) {
                        // 移动网络已连接
                        //tv.text = "移动网络已连接—NetworkStatusReceiver"
                    }
                    onNetAvailable()
                } else {
                    // 网络不可用
                    //tv.text = "网络不可用—NetworkStatusReceiver"
                    onNetUnavailable()
                }
            }
        }
    }

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    private fun listeningNetwork() {
        // Android 5.0 及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 经过测试，使用 networkCallback 来监测网络状态，
            // 如果app启动前网络可用，那么 onAvailable( ) 方法会被调用;
            // 如果app启动前网络不可用，在app刚启动时，onLost( ) 方法却不会被调用，
            // 所以需要我们自己来监测一下初始网络状态，如果是不可用，则执行无网需要执行的操作
            val activeInfo = mConnectivityManager?.activeNetworkInfo
            if (activeInfo == null || !activeInfo.isConnected || !activeInfo.isAvailable) {
                //tv.text = "网络不可用—NetworkCallback"
                onNetUnavailable()
            }
            val request = NetworkRequest.Builder()
                // NetworkCapabilities.NET_CAPABILITY_INTERNET 表示此网络应该能够连接到Internet
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                // NetworkCapabilities.TRANSPORT_WIFI 表示该网络使用Wi-Fi传输
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                // NetworkCapabilities.TRANSPORT_CELLULAR 表示此网络使用蜂窝传输
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()

            mNetworkCallback = object : ConnectivityManager.NetworkCallback() {

                /**
                 * 当 framework 连接并已声明新网络可供使用时调用
                 */
                override fun onAvailable(network: Network) {
                    val activeInfo = mConnectivityManager?.activeNetworkInfo
                    val type = activeInfo?.type
                    wifiConnected = type == ConnectivityManager.TYPE_WIFI
                    mobileConnected = type == ConnectivityManager.TYPE_MOBILE
                    activity?.apply {
                        runOnUiThread {
                            if (wifiConnected) {
                                // wifi已连接
                                //tv.text = "wifi已连接—NetworkCallback"
                                onNetAvailable()
                            } else if (mobileConnected) {
                                // 移动网络已连接
                                //tv.text = "移动网络已连接—NetworkCallback"
                                onNetAvailable()
                            }
                        }
                    }
                }

                /**
                 * 当此请求的 framework 连接到的网络更改功能，但仍满足所述需求时调用。
                 */
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    LogUtil.i(
                        TAG,
                        "onCapabilitiesChanged,NetworkCapabilities -> $networkCapabilities"
                    )
                }

                /**
                 * 丢失网络时调用
                 */
                override fun onLost(network: Network) {
                    activity?.apply {
                        runOnUiThread {
                            //tv.text = "网络不可用—NetworkCallback"
                            onNetUnavailable()
                        }
                    }
                }
            }
            mConnectivityManager?.registerNetworkCallback(request, mNetworkCallback)
        } else {
            // 注册网络状态接收者
            registerNetworkStatusReceiver()
        }
    }

    private fun unregisterNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mNetworkCallback != null) {
                LogUtil.i(TAG, "Unregistering network callback")
                mConnectivityManager?.unregisterNetworkCallback(mNetworkCallback)
                mNetworkCallback = null
            }
        }
    }

    /**
     * 注册网络状态广播接收器
     */
    private fun registerNetworkStatusReceiver() {
        // 向filter中添加 ConnectivityManager.CONNECTIVITY_ACTION 以监听网络
        activity?.apply {
            val intentFilter = IntentFilter()
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            mNetWorkReceiver = NetworkStatusReceiver()
            registerReceiver(mNetWorkReceiver, intentFilter)
        }
        needUnregisterReceiver = true
    }

    /**
     * 网络不可用
     */
    override fun onNetUnavailable() {
        LogUtil.i(TAG, "onNetUnavailable")
        if (mSnackbar == null) {
            val currentView = mRootView?.findViewById<ViewGroup>(zContentViewId())
            currentView?.apply {
                mSnackbar = Snackbar.make(currentView, "当前网络不可用", Snackbar.LENGTH_INDEFINITE)
                    .setAction("前往设置") {
                        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        })
                    }
            }
        }
        mSnackbar?.apply {
            if (!isShown) {
                show()
            }
        }
    }

    /**
     * 网络可用
     */
    override fun onNetAvailable() {
        LogUtil.i(TAG, "onNetAvailable")
        mSnackbar?.apply {
            if (isShown) {
                dismiss()
            }
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

    override fun zHideLoadDialog(flag: Int) {
        mZStatusView?.hideLoadDialog()
    }

    override fun zShowLoadDialog(flag: Int, msg: String?) {
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