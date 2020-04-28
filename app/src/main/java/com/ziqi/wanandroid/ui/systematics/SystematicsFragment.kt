package com.ziqi.wanandroid.ui.systematics

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ziqi.baselibrary.base.ZBaseFragment
import com.ziqi.baselibrary.mvvm.ViewModelFragment
import com.ziqi.baselibrary.view.status.ZStatusViewBuilder
import com.ziqi.wanandroid.R
import com.ziqi.wanandroid.bean.Tree
import com.ziqi.wanandroid.databinding.FragmentSystematicsBinding
import com.ziqi.wanandroid.databinding.FragmentSystematicsContentBinding
import com.ziqi.wanandroid.databinding.FragmentSystematicsContentMenuCategoryBinding
import java.util.*

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/15 11:37 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class SystematicsFragment :
    ViewModelFragment<SystematicsViewModel, Parcelable, FragmentSystematicsBinding>(),
    SwipeRefreshLayout.OnRefreshListener {


    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?): SystematicsFragment {
            var mWBaseFragment = SystematicsFragment()
            mWBaseFragment.arguments = bundle
            return mWBaseFragment
        }
    }

    private var mFirstAdapter: BaseQuickAdapter<Tree, BaseViewHolder>? = null

    private var mSecondAdapter: BaseQuickAdapter<Tree.ChildrenBean, BaseViewHolder>? = null

    private var tabTexts = arrayListOf("分类")

    private var mMenuCategoryBinding: FragmentSystematicsContentMenuCategoryBinding? = null

    private var mContentViewBinding: FragmentSystematicsContentBinding? = null

    override fun onClick(v: View?) {

    }

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_systematics
    }

    override fun zContentViewId(): Int {
        return R.id.myRootView
    }


    override fun zVisibleToUser(isNewIntent: Boolean) {

    }

    override fun zLazyVisible() {
        super.zLazyVisible()
        mZStatusView?.config(
            ZStatusViewBuilder.Builder()
                .setOnErrorRetryClickListener {
                    zStatusLoadingView()
                    onRefresh()
                }
                .setOnEmptyRetryClickListener {
                    zStatusLoadingView()
                    onRefresh()
                }
                .build())
        initView()
        mViewModel?.mTree?.observe(viewLifecycleOwner, Observer {
            mFirstAdapter?.setNewData(it)
        })
        onRefresh()
    }

    private fun initView() {
        //==================================================================
        val menuCategoryView = LayoutInflater
            .from(activity)
            .inflate(R.layout.fragment_systematics_content_menu_category, null)
        val popupViews = arrayListOf(menuCategoryView)
        //==================================================================
        val contentView = LayoutInflater
            .from(activity)
            .inflate(R.layout.fragment_systematics_content, null)
        //==================================================================
        mViewDataBinding?.dropDownMenu?.setDropDownMenu(tabTexts, popupViews, contentView)
        //==================================================================
        mMenuCategoryBinding = DataBindingUtil.bind(menuCategoryView)
        //==================================================================
        mFirstAdapter = object :
            BaseQuickAdapter<Tree, BaseViewHolder>(
                R.layout.fragment_systematics_content_menu_category_item,
                null
            ) {
            override fun convert(helper: BaseViewHolder?, item: Tree?) {
                helper?.setText(R.id.tv, item?.name)
            }
        }
        mFirstAdapter?.setOnItemClickListener { _, _, position ->
            val item = mFirstAdapter?.data?.get(position)
            item?.apply {
                val newList = mutableListOf<Tree.ChildrenBean>()
                newList.addAll(children)
                Collections.copy(newList, children)
                mSecondAdapter?.setNewData(newList)
            }
        }
        mMenuCategoryBinding?.recyclerViewFrist?.adapter = mFirstAdapter
        mMenuCategoryBinding?.recyclerViewFrist?.layoutManager = LinearLayoutManager(context)
        mMenuCategoryBinding?.recyclerViewFrist?.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        //==================================================================
        mSecondAdapter = object :
            BaseQuickAdapter<Tree.ChildrenBean, BaseViewHolder>(
                R.layout.fragment_systematics_content_menu_category_item,
                null
            ) {
            override fun convert(helper: BaseViewHolder?, item: Tree.ChildrenBean?) {
                helper?.setText(R.id.tv, item?.name)
            }
        }
        mSecondAdapter?.setOnItemClickListener { _, _, position ->

        }
        mMenuCategoryBinding?.recyclerViewSecond?.adapter = mSecondAdapter
        mMenuCategoryBinding?.recyclerViewSecond?.layoutManager = LinearLayoutManager(context)
        mMenuCategoryBinding?.recyclerViewSecond?.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        //==================================================================
        mContentViewBinding = DataBindingUtil.bind(contentView)
        //==================================================================
    }

    override fun onRefresh() {
        mViewModel?.loadTree()
    }
}