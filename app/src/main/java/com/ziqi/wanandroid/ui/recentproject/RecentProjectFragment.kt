package com.ziqi.wanandroid.ui.recentproject

import android.os.Parcelable
import android.text.Html
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.animation.AlphaInAnimation
import com.ziqi.baselibrary.common.WebInfo
import com.ziqi.baselibrary.mvvm.ViewModelFragment
import com.ziqi.baselibrary.util.StringUtil
import com.ziqi.wanandroid.R
import com.ziqi.wanandroid.bean.Article
import com.ziqi.wanandroid.bean.ListProject
import com.ziqi.wanandroid.bean.WanList
import com.ziqi.wanandroid.databinding.FragmentProjectBinding
import com.ziqi.wanandroid.databinding.FragmentProjectBindingImpl
import com.ziqi.wanandroid.databinding.FragmentRecentBlogBinding
import com.ziqi.wanandroid.databinding.FragmentRecentProjectBinding
import com.ziqi.wanandroid.ui.common.BaseFragment
import com.ziqi.wanandroid.ui.recentblog.RecentBlogViewModel
import com.ziqi.wanandroid.util.ImageLoad
import com.ziqi.wanandroid.util.StartUtil

class RecentProjectFragment :
    BaseFragment<RecentProjectViewModel, Parcelable, FragmentRecentProjectBinding>() {

    companion object {
        fun newInstance() = RecentProjectFragment()
    }


    private lateinit var mAdapter: BaseQuickAdapter<ListProject.DatasBean, BaseViewHolder>

    var mData: ListProject? = null

    override fun onClick(v: View?) {
    }

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_recent_project
    }

    override fun zContentViewId(): Int {
        return R.id.myRootView
    }

    override fun zVisibleToUser(isNewIntent: Boolean) {
    }

    override fun zLazyVisible() {
        super.zLazyVisible()
        initView()
        dealViewModel()
        onRefresh()
    }

    override fun initView() {
        mAdapter =
            object : BaseQuickAdapter<ListProject.DatasBean, BaseViewHolder>(
                R.layout.fragment_recent_project_item,
                null
            ) {
                override fun convert(holder: BaseViewHolder, item: ListProject.DatasBean) {
                    holder.setText(
                        R.id.author,
                        StringUtil.emptyTip(item.author, item.shareUser ?: "暂无")
                    )
                    holder.setText(
                        R.id.desc,
                        StringUtil.emptyTip(item.desc, "暂无介绍")
                    )
                    holder.setText(R.id.title, Html.fromHtml(item.title))
                    holder.setText(R.id.niceDate, """时间：${item.niceDate?.trim()}""")
                    holder.setText(
                        R.id.chapterName, """${item.chapterName}/${item.superChapterName}"""
                    )
                    ImageLoad.loadUrl(
                        this@RecentProjectFragment,
                        item.envelopePic,
                        holder.getView(R.id.envelopePic)
                    )
                    holder.getView<LinearLayout>(R.id.content).setOnClickListener {
                        activity?.let {
                            val webInfo = WebInfo()
                            webInfo.url = item.link
                            StartUtil.startWebFragment(it, this@RecentProjectFragment, -1, webInfo)
                        }
                    }
                }
            }

        mAdapter.openLoadAnimation(AlphaInAnimation())
        mViewDataBinding?.recyclerview?.adapter = mAdapter
        //=================================================================================
        mViewDataBinding?.recyclerview?.layoutManager = LinearLayoutManager(context)
        mViewDataBinding?.recyclerview?.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        mAdapter.setOnItemClickListener { _, _, _ ->

        }
        //https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/8-LoadMore.md
        mAdapter.setOnLoadMoreListener({
            mViewModel?.loadListProject(mData?.curPage ?: 1)
        }, mViewDataBinding?.recyclerview)
        mViewDataBinding?.myRootView?.setOnRefreshListener(this)
    }

    override fun dealViewModel() {
        mViewModel?.mRefresh?.observe(viewLifecycleOwner, Observer {
            mViewDataBinding?.myRootView?.isRefreshing = false
        })
        mViewModel?.mLoadMore?.observe(viewLifecycleOwner, Observer {
            when (it.getContentIfNotHandled()) {
                true -> {
                    mAdapter.loadMoreComplete()
                }
                false -> {
                    mAdapter.loadMoreFail()
                }
            }
        })
        mViewModel?.mListProject?.observe(viewLifecycleOwner, Observer {
            if ((it.curPage < it.pageCount)) {
                it.datas?.apply {
                    mAdapter.addData(this)
                }
                mAdapter.setEnableLoadMore(true)
            } else {
                mAdapter.setEnableLoadMore(false)
                mAdapter.loadMoreEnd()
            }
            mData = it
        })
    }

    override fun onRefresh() {
        mViewModel?.loadListProject(0)
    }

}
