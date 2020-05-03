package com.ziqi.baselibrary.view.webview

import android.content.Context
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.webkit.*
import androidx.annotation.RequiresApi
import com.ziqi.baselibrary.util.FileUtil
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.baselibrary.util.SystemTool
import com.ziqi.baselibrary.view.webview.OpenWeb.openUrl
import java.io.File
import java.util.*

/**
 * Copyright (C), 2018-2018
 * FileName: TouchWebView
 * Author: ziqimo
 * Date: 2018/11/28 下午9:27
 * Description: ${DESCRIPTION}
 * History:https://www.jianshu.com/p/3c94ae673e2a/
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class TouchWebView : WebView {
    @Volatile
    private var isFinish = false
    /**
     * 回调接口
     */
    private var simulationListener: SimulationListener? = null
    /**
     * 设置包名
     */
    private var packageName: String? = null
    /**
     * 是否开启拦截
     */
    var isShouldInterceptRequest = false
    /**
     * reloadURL链接
     */
    private var reloadURL: String? = null

    fun setSimulationListener(simulationListener: SimulationListener?) {
        this.simulationListener = simulationListener
    }

    @JvmOverloads
    constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = -1
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        // 在Android 3.0以下 去除远程代码执行漏洞
        removeJavascriptInterface("searchBoxJavaBridge_")
        removeJavascriptInterface("accessibility")
        removeJavascriptInterface("accessibilityTraversal")
        //声明WebSettings子类
        val webSettings = this.settings
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.javaScriptEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            addJavascriptInterface(InJavaScriptLocalObj(this), "java_obj")
        } else {
            //不处理
        }
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        //支持插件
        //webSettings.setPluginsEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.displayZoomControls = false //隐藏原生的缩放控件
        //其他细节操作
        webSettings.allowFileAccess = true //设置可以访问文件
        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        // 加快HTML网页加载完成的速度，等页面finish再加载图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.loadsImagesAutomatically = true
        } else {
            webSettings.loadsImagesAutomatically = false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0 以后的WebView加载的链接为Https开头，但是链接里面的内容，
            // 比如图片为Http链接，这时候，图片就会加载不出来
            // 下面两者都可以
            // Android 5.0上Webview默认不允许加载Http与Https混合内容
            // ws.setMixedContentMode(ws.getMixedContentMode())
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        // 4.1以后默认禁止
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.allowFileAccessFromFileURLs = false
            webSettings.allowUniversalAccessFromFileURLs = false
        }
        webSettings.defaultTextEncodingName = "utf-8" //设置编码格式
        // 定位相关
        webSettings.setGeolocationEnabled(false)
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webSettings.domStorageEnabled = true // 开启 DOM storage API 功能
        webSettings.databaseEnabled = true //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true) //开启 Application Caches 功能
        val cacheDirPath = context.filesDir.absolutePath + APP_CACAHE_DIRNAME
        webSettings.setAppCachePath(cacheDirPath) //设置  Application Caches 缓存目录
        // 不保存密码，已经废弃了该方法，以后的版本都不会保存密码
        webSettings.savePassword = false
        //webSettings.setUserAgentString(UAHelper.instance(getContext()));
        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {
                if (url.startsWith("http"))
                    loadURLInner(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                LogUtil.i(tAG, "onPageFinished>>>$url")
                simulationListener?.onPageFinished(url)
                inFinish()
            }

            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
                LogUtil.i(tAG, "onPageStarted>>>$url")
            }

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) { //super.onReceivedSslError(view, handler, error);
                handler.proceed()
            }

            override fun shouldInterceptRequest(
                view: WebView,
                url: String
            ): WebResourceResponse? { //LogUtil.i(getTAG(), Thread.currentThread().getName() + ".shouldInterceptRequest>" + url);
                return super.shouldInterceptRequest(view, url)
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? { //LogUtil.i(getTAG(), Thread.currentThread().getName() + ".shouldInterceptRequest5.0>" + url);
                return super.shouldInterceptRequest(view, request)
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                val url = request.url.toString()
                LogUtil.i(tAG, "onReceivedError5.0:$url")
                if (request.isForMainFrame || url === getUrl()) {
                    simulationListener?.onError(url)
                    reloadURL = url
                    isFinish = true
                    if (SystemTool.checkNet(context) != SystemTool.NETWORK_NONE) {
                        //openUrl(context, url)
                    }
                }
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return
                }
                LogUtil.i(
                    tAG,
                    "onReceivedError.failingUrl$failingUrl"
                )
                simulationListener?.onError(failingUrl)
                reloadURL = failingUrl
                isFinish = true
                if (SystemTool.checkNet(context) != SystemTool.NETWORK_NONE) {
                    //openUrl(context, failingUrl)
                }
            }
        }
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                LogUtil.i(tAG, "onProgressChanged>>>$newProgress");
                if (newProgress >= 100) {
                    inFinish()
                    simulationListener?.onProgressChanged(newProgress)
                    simulationListener?.onPageFinished(url)
                    //getReferrer("onProgressChanged");
                } else {
                    simulationListener?.onProgressChanged(newProgress)
                }
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                simulationListener?.onReceivedTitle(title)

            }
        }
    }

    private fun inFinish() {
        if (!isFinish) {
            isFinish = true
            LogUtil.i(
                tAG,
                "inFinish.thread_name:" + Thread.currentThread().name
            )
            simulationListener?.doSimulation()
        }
    }

    val tAG: String
        get() = "TouchWebView"

    fun setCurrentPackageName(packageName: String?) {
        this.packageName = packageName
    }

    /**
     * 加载url地址
     *
     * @param url
     */
    fun loadURL(url: String) {
        loadURLInner(url)
        isFinish = false
    }

    /**
     * 内部方法
     *
     * @param url
     */
    private fun loadURLInner(url: String) {
        if (TextUtils.isEmpty(packageName)) {
            loadUrl(url)
        } else {
            val headerMap: MutableMap<String, String?> = HashMap()
            headerMap["X-Requested-With"] = packageName
            loadUrl(url, headerMap)
        }
    }

    fun loadJs(js: String) {
        LogUtil.i(tAG, "loadJs.js:$js")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            evaluateJavascript(js) { value ->
                LogUtil.i(tAG, "loadJs.value:$value")
            }
        } else {
            loadURLInner(js)
        }
    }

    /**
     * 获取地址
     *
     * @return
     */
    fun getReloadURL(): String {
        return if (TextUtils.isEmpty(reloadURL)) url else reloadURL!!
    }

    /**
     * 获取页面元素
     */
    val html: Unit
        get() {
            loadJs("javascript:window.java_obj.showSource(document.getElementsByTagName('html')[0].innerHTML);")
        }

    /**
     * 获取页面是否执行
     */
    val loadCompete: Unit
        get() {
            loadJs("javascript:window.java_obj.readyState(document.readyState);")
        }

    /**
     * 获取Referrer
     */
    fun getReferrer(from: String) {
        LogUtil.i(tAG, "$from>>>getReferrer")
        // 获取页面内容
        loadJs("javascript:window.java_obj.showReferrer(document.referrer);")
    }

    fun getSource(html: String?) {}
    fun readyState(readyState: String?) {}

    companion object {
        private const val APP_CACAHE_DIRNAME = "webview_cache"
        @Throws(Exception::class)
        fun clearCookies(webView: WebView) {
            try {
                webView.clearCache(true)
                webView.clearHistory()
                webView.clearFormData()
                CookieSyncManager.createInstance(webView.context)
                val cookieManager =
                    CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                cookieManager.removeSessionCookie()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    cookieManager.removeAllCookies(null)
                    CookieManager.getInstance().flush()
                } else {
                    cookieManager.removeAllCookie()
                    CookieSyncManager.getInstance().sync()
                }
                WebStorage.getInstance().deleteAllData()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val absolutePath = webView.context.filesDir.parentFile.absolutePath
            val WebViewChromiumPrefs =
                absolutePath + File.separator + "shared_prefs" + File.separator + "WebViewChromiumPrefs.xml"
            val app_webview = absolutePath + File.separator + "app_webview"
            val cache = absolutePath + File.separator + "cache"
            FileUtil.deleteDir(WebViewChromiumPrefs)
            FileUtil.deleteDir(app_webview)
            FileUtil.deleteDir(cache)
            FileUtil.deleteDir("data/data/" + webView.context.packageName + File.separator + "shared_prefs" + File.separator + "WebViewChromiumPrefs.xml")
            clearWebViewCache(webView.context)
            try {
                webView.context.deleteDatabase("webview.db")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                webView.context.deleteDatabase("webviewCache.db")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun clearWebViewCache(context: Context) {
            val file = context.cacheDir.absoluteFile
            FileUtil.deleteDir(file.absolutePath)
        }

    }
}