package com.ziqi.wanandroid.commonlibrary.util.route

/**
 *    作者 : moziqi
 *    邮箱 : 709847739@qq.com
 *    时间   : 2020/5/6-11:08
 *    desc   :
 *    version: 1.0
 */
interface IStartPage {

    @StartActivity(value = "com.ziqi.wanandroid.ui.main.MainActivity")
    fun toMain()

    @StartFragment(value = "com.ziqi.wanandroid.me.ui.wxarticle.WxArticleFragment")
    @StartActivity(value = "com.ziqi.wanandroid.commonlibrary.ui.common.CommonActivity")
    fun toWxArticle()

    @StartFragment(value = "com.ziqi.wanandroid.me.ui.wxarticle.WxArticleFragment")
    fun toCollect()
}