package com.ziqi.baselibrary.view.webview

import android.webkit.JavascriptInterface
import com.ziqi.baselibrary.util.LogUtil.i

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2019/6/5-12:39
 * desc   :
 * version: 1.0
 */
internal class InJavaScriptLocalObj(private val touchWebView: TouchWebView) {
    @JavascriptInterface
    fun showSource(html: String) {
        i("====>showSource=$html")
        touchWebView.getSource(html)
    }

    @JavascriptInterface
    fun readyState(readyState: String) {
        i("====>readyState=$readyState")
        touchWebView.readyState(readyState)
    }

    @JavascriptInterface
    fun showDescription(str: String) {
        i("====>showDescription=$str")
    }

    @JavascriptInterface
    fun showReferrer(str: String) {
        i("====>showReferrer=$str")
    }

}