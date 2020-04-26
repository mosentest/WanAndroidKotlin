package com.ziqi.wanandroid.ui.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ziqi.wanandroid.bean.Article

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2020/4/26-18:10
 * desc   :
 * version: 1.0
 */
abstract class LoadMoreAdapter<T>(layoutResId: Int, data: MutableList<T>?) :
    BaseQuickAdapter<T, BaseViewHolder>(layoutResId, data), LoadMoreModule
