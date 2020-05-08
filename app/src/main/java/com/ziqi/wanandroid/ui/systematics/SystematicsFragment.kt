package com.ziqi.wanandroid.ui.systematics

import android.os.Bundle
import android.os.Parcelable
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.animation.AlphaInAnimation
import com.ziqi.baselibrary.common.WebInfo
import com.ziqi.baselibrary.util.StringUtil
import com.ziqi.baselibrary.view.status.ZStatusView
import com.ziqi.baselibrary.view.status.ZStatusViewBuilder
import com.ziqi.wanandroid.R
import com.ziqi.wanandroid.commonlibrary.bean.Article
import com.ziqi.wanandroid.commonlibrary.bean.Tree
import com.ziqi.wanandroid.commonlibrary.bean.WanList
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseFragment
import com.ziqi.wanandroid.commonlibrary.util.StartUtil
import com.ziqi.wanandroid.databinding.FragmentSystematicsBinding
import com.ziqi.wanandroid.databinding.FragmentSystematicsContentBinding
import com.ziqi.wanandroid.databinding.FragmentSystematicsContentMenuCategoryBinding

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
    BaseFragment<SystematicsViewModel, Parcelable, FragmentSystematicsBinding>() {


    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?): SystematicsFragment {
            var mWBaseFragment = SystematicsFragment()
            mWBaseFragment.arguments = bundle
            return mWBaseFragment
        }
    }

    private var mFirstPosition: Int = 0
    /**
     * 临时变量
     */
    private var mTempFirstPosition: Int = 0

    /**
     * 标记当前选中的位置参数
     */
    private var mCurrentTree: Tree? = null

    private var mFirstAdapter: BaseQuickAdapter<Tree, BaseViewHolder>? = null

    private var mSecondAdapter: BaseQuickAdapter<Tree.ChildrenBean, BaseViewHolder>? = null

    private var tabTexts = arrayListOf("体系分类")

    private var mMenuCategoryBinding: FragmentSystematicsContentMenuCategoryBinding? = null

    private var mContentViewBinding: FragmentSystematicsContentBinding? = null

    private var mContentAdapter: BaseQuickAdapter<Article, BaseViewHolder>? = null

    private var mContentViewStatusView: ZStatusView? = null

    private var mData: WanList<Article>? = null


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
                    mViewModel?.loadTree()
                }
                .setOnEmptyRetryClickListener {
                    zStatusLoadingView()
                    mViewModel?.loadTree()
                }
                .build())
        initView()
        dealViewModel()
        mViewModel?.loadTree()
    }

    override fun initView() {
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
        mViewDataBinding?.dropDownMenu?.apply {
            //通过反射获取原本的位置
            val maskViewField = javaClass.getDeclaredField("maskView")
            maskViewField.isAccessible = true
            val maskView: View = maskViewField.get(this) as View
            maskView.setOnClickListener {
                if (mTempFirstPosition != mFirstPosition) {
                    mFirstAdapter?.data?.apply {
                        for (tree in this) {
                            tree.userControlSetTop = false
                        }
                        mCurrentTree = get(mFirstPosition)
                        mCurrentTree?.userControlSetTop = true
                        //刷新数据
                        mFirstAdapter?.notifyDataSetChanged()
                        mSecondAdapter?.setNewData(mCurrentTree?.children)
                    }
                }
                mMenuCategoryBinding?.recyclerViewFrist?.scrollToPosition(mFirstPosition)
                closeMenu()
            }
        }
        //==================================================================
        mMenuCategoryBinding = DataBindingUtil.bind(menuCategoryView)
        //==================================================================
        mFirstAdapter = object :
            BaseQuickAdapter<Tree, BaseViewHolder>(
                R.layout.fragment_systematics_content_menu_category_item,
                null
            ) {
            override fun convert(helper: BaseViewHolder, item: Tree) {
                helper.setText(R.id.tv, item.name)
                helper.getView<TextView>(R.id.tv).isSelected = item.userControlSetTop
            }
        }
        mFirstAdapter?.setOnItemClickListener { _, _, position ->
            mFirstAdapter?.data?.let {
                //点击的时候，遍历改为false
                for (tree in it) {
                    tree.userControlSetTop = false
                }
                //当前选择的位置
                mTempFirstPosition = position
                val item = it[mTempFirstPosition]
                item.userControlSetTop = true
                //刷新第二屏幕的数据
                mSecondAdapter?.setNewData(item.children)
            }
            mFirstAdapter?.notifyDataSetChanged()
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
            override fun convert(helper: BaseViewHolder, item: Tree.ChildrenBean) {
                helper.setText(R.id.tv, item.name)
                helper.getView<TextView>(R.id.tv).isSelected = item.userControlSetTop
            }
        }
        mSecondAdapter?.setOnItemClickListener { _, _, position ->
            //遍历所有的改为false
            mFirstAdapter?.data?.let {
                for (tree in it) {
                    for (child in tree.children) {
                        child.userControlSetTop = false
                    }
                }
            }
            //处理当前位置的
            mSecondAdapter?.data?.let {
                //修改这个true
                it[position].userControlSetTop = true
            }
            mSecondAdapter?.notifyDataSetChanged()
            //点击过这里才去赋值
            mFirstPosition = mTempFirstPosition
            //找到第一个数据
            mCurrentTree = mFirstAdapter?.let {
                it.data[mFirstPosition]
            }
            loadData(true, 0)
            mViewDataBinding?.dropDownMenu?.closeMenu()
        }
        mMenuCategoryBinding?.recyclerViewSecond?.adapter = mSecondAdapter
        mMenuCategoryBinding?.recyclerViewSecond?.layoutManager = LinearLayoutManager(context)
        mMenuCategoryBinding?.recyclerViewSecond?.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        initContentView(contentView)
    }

    private fun initContentView(contentView: View) {
        //==================================================================
        mContentViewBinding = DataBindingUtil.bind(contentView)
        //=========================设置content的状态view=====================
        mContentViewStatusView = ZStatusView.init(contentView, R.id.myRootView)
        mContentViewStatusView?.config(
            ZStatusViewBuilder.Builder()
                .setOnErrorRetryClickListener {
                    mContentViewStatusView?.showLoadingView()
                    onRefresh()
                }
                .setOnEmptyRetryClickListener {
                    mContentViewStatusView?.showLoadingView()
                    onRefresh()
                }
                .build())
        //===================跟首页可以共用一个item布局========================
        mContentAdapter =
            object : BaseQuickAdapter<Article, BaseViewHolder>(
                R.layout.fragment_recent_blog_item,
                null
            ) {
                override fun convert(holder: BaseViewHolder, item: Article) {
                    holder.setText(
                        R.id.author,
                        StringUtil.emptyTip(item.author, item.shareUser ?: "暂无")
                    )
                    holder.setText(R.id.title, Html.fromHtml(item.title))
                    holder.setText(R.id.niceDate, """时间：${item.niceDate?.trim()}""")
                    holder.setText(
                        R.id.chapterName, """${item.chapterName}/${item.superChapterName}"""
                    )
                    holder.getView<LinearLayout>(R.id.content).setOnClickListener {
                        activity?.let {
                            val webInfo = WebInfo()
                            webInfo.url = item.link
                            StartUtil.startWebFragment(it, this@SystematicsFragment, -1, webInfo)
                        }
                    }
                }
            }

        mContentAdapter?.openLoadAnimation(AlphaInAnimation())
        mContentViewBinding?.recyclerview?.adapter = mContentAdapter
        //=================================================================================
        mContentViewBinding?.recyclerview?.layoutManager = LinearLayoutManager(context)
        mContentViewBinding?.recyclerview?.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        mContentAdapter?.setOnItemClickListener { _, _, _ ->

        }
        //https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/8-LoadMore.md
        mContentAdapter?.setOnLoadMoreListener({
            val curPage = mData?.curPage ?: 1
            val pageCount = mData?.pageCount ?: 1
            if (curPage >= pageCount) {
                mContentAdapter?.loadMoreEnd()
            } else {
                loadData(false, curPage)
            }
        }, mContentViewBinding?.recyclerview)
        mContentViewBinding?.myRootView?.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        loadData(false, 0)
    }

    private fun loadData(showLoading: Boolean, pos: Int) {
        val children = mCurrentTree?.children?.let {
            var temp = -1
            for (i in it.indices) {
                val tempChild = it[i]
                if (tempChild.userControlSetTop) {
                    temp = i
                    break
                }
            }
            if (temp == -1) {
                temp = 0
            }
            mSecondAdapter?.notifyItemChanged(temp)
            it[temp]
        }
        mViewModel?.loadArticleList(showLoading, pos, children?.id ?: pos)
    }

    override fun dealViewModel() {
        mViewModel?.mRefresh?.observe(viewLifecycleOwner, Observer {
            mContentViewBinding?.myRootView?.isRefreshing = false
        })
        mViewModel?.mLoadMore?.observe(viewLifecycleOwner, Observer {
            it?.apply {
                when (getContentIfNotHandled()) {
                    true -> {
                        mContentAdapter?.loadMoreComplete()
                    }
                    false -> {
                        mContentAdapter?.loadMoreFail()
                    }
                }
            }
        })
        mViewModel?.mContentStatusView?.observe(viewLifecycleOwner, Observer {
            it.apply {
                when (getContentIfNotHandled()) {
                    1 -> {
                        mContentViewStatusView?.showContentView()
                    }
                    else -> {
                        mContentViewStatusView?.showErrorView()
                    }
                }
            }
        })
        mViewModel?.mTree?.observe(viewLifecycleOwner, Observer {
            it?.apply {
                if (this.size > 0) {
                    var temp = -1
                    for (i in this.indices) {
                        if (this[i].userControlSetTop) {
                            temp = i
                            break
                        }
                    }
                    if (temp == -1) {
                        temp = 0
                        this[0].userControlSetTop = true
                        this[0].children[0].userControlSetTop = true
                    }
                    mCurrentTree = this[temp]
                    //给第二栏目设置数据同时刷新下面的列表
                    mSecondAdapter?.setNewData(mCurrentTree?.children)
                    mContentViewStatusView?.showLoadingView()
                    onRefresh()
                } else {
                    //中间显示空
                    mContentViewStatusView?.showEmptyView()
                }
                mFirstAdapter?.setNewData(this)
            }
        })
        mViewModel?.mArticleList?.observe(viewLifecycleOwner, Observer {
            it?.apply {
                datas?.apply {
                    if (curPage <= 1) {
                        mContentAdapter?.setNewData(this)
                    } else {
                        mContentAdapter?.addData(this)
                    }
                }
                mContentAdapter?.setEnableLoadMore(pageCount > 1)
                mData = this
            }
        })
    }
}