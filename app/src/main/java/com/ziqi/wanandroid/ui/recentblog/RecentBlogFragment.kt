package com.ziqi.wanandroid.ui.recentblog

import android.os.Parcelable
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.animation.AlphaInAnimation
import com.ziqi.baselibrary.common.WebInfo
import com.ziqi.baselibrary.util.StringUtil
import com.ziqi.baselibrary.view.status.ZStatusViewBuilder
import com.ziqi.wanandroid.R
import com.ziqi.wanandroid.commonlibrary.bean.Article
import com.ziqi.wanandroid.commonlibrary.bean.WanList
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseFragment
import com.ziqi.wanandroid.commonlibrary.util.StartUtil
import com.ziqi.wanandroid.commonlibrary.view.LinearLayoutManagerX
import com.ziqi.wanandroid.databinding.FragmentRecentBlogBinding

class RecentBlogFragment :
    BaseFragment<RecentBlogViewModel, Parcelable, FragmentRecentBlogBinding>() {

    companion object {
        fun newInstance() = RecentBlogFragment()
    }


    private var mAdapter: BaseQuickAdapter<Article, BaseViewHolder>? = null

    private var mCurrentData: WanList<Article>? = null


    override fun zSetLayoutId(): Int {
        return R.layout.fragment_recent_blog
    }

    override fun zContentViewId(): Int {
        return R.id.myRootView
    }

    override fun onClick(v: View?) {

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
        dealViewModel()
        onRefresh()
    }

    override fun onRefresh() {
        mViewModel?.loadArticleTop(false)
    }

    override fun initView() {
        mAdapter =
            object : BaseQuickAdapter<Article, BaseViewHolder>(
                R.layout.fragment_recent_blog_item,
                null
            ) {
                override fun convert(holder: BaseViewHolder, item: Article) {
                    holder.setText(
                        R.id.author,
                        StringUtil.emptyTip(
                            item.author,
                            item.shareUser ?: getString(R.string.common_no_info)
                        )
                    )
                    holder.setText(R.id.title, Html.fromHtml(item.title))
                    holder.setText(
                        R.id.niceDate, String.format(
                            getString(
                                R.string.common_with_time_tip,
                                item.niceDate?.trim()
                            )
                        )
                    )
                    holder.setText(
                        R.id.chapterName, """${item.chapterName}/${item.superChapterName}"""
                    )

                    holder.getView<ImageView>(R.id.ivCollect).isSelected = ("true" == item.collect)

                    holder.addOnClickListener(R.id.llCollect, R.id.content)
                }
            }

        mAdapter?.openLoadAnimation(AlphaInAnimation())
        mViewDataBinding?.recyclerview?.adapter = mAdapter
        //=================================================================================
        mViewDataBinding?.recyclerview?.layoutManager = LinearLayoutManagerX(context)
        mViewDataBinding?.recyclerview?.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        mAdapter?.setOnItemClickListener { _, _, _ ->

        }
        mAdapter?.setOnItemChildClickListener { _, view, position ->
            val cData = mAdapter?.data?.get(position)
            when (view.id) {
                R.id.llCollect -> {
                    toLogin(object : LoginListener {
                        override fun onSuccess() {
                            if (cData?.collect == "true") {
                                mViewModel?.lgUncollectOriginId(cData.id?.toInt(), position)
                            } else {
                                mViewModel?.lgCollect(cData?.id?.toInt(), position)
                            }
                        }

                        override fun onCancel() {
                        }

                    }, null)
                }
                R.id.content -> {
                    activity?.let {
                        val webInfo = WebInfo()
                        webInfo.url = cData?.link
                        StartUtil.startWebFragment(it, this, -1, webInfo)
                    }
                }
                else -> {
                }
            }
        }
        //https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/8-LoadMore.md
        mAdapter?.setOnLoadMoreListener({
            val curPage = mCurrentData?.curPage ?: 1
            val pageCount = mCurrentData?.pageCount ?: 1
            if (curPage >= pageCount) {
                mAdapter?.loadMoreEnd()
            } else {
                mViewModel?.loadArticleList(curPage)
            }
        }, mViewDataBinding?.recyclerview)
        mViewDataBinding?.myRootView?.setOnRefreshListener(this)
    }

    override fun dealViewModel() {
        mViewModel?.mRefresh?.observe(viewLifecycleOwner, Observer {
            mViewDataBinding?.myRootView?.isRefreshing = false
        })
        mViewModel?.mLoadMore?.observe(viewLifecycleOwner, Observer {
            it?.apply {
                when (getContentIfNotHandled()) {
                    true -> {
                        mAdapter?.loadMoreComplete()
                    }
                    false -> {
                        mAdapter?.loadMoreFail()
                    }
                }
            }
        })
        mViewModel?.mArticleTop?.observe(viewLifecycleOwner, Observer {
            mAdapter?.setNewData(it)
        })
        mViewModel?.mArticleList?.observe(viewLifecycleOwner, Observer {
            it?.apply {
                datas?.apply {
                    if (curPage <= 1) {
                        if (isEmpty()) {
                            mZStatusView?.showEmptyView()
                        } else {
                            mAdapter?.setNewData(this)
                        }
                    } else {
                        mAdapter?.addData(this)
                    }
                }
                mAdapter?.setEnableLoadMore(pageCount > 1)
                mCurrentData = this
            }
        })
        mViewModel?.mLgCollect?.observe(viewLifecycleOwner, Observer {
            mAdapter?.data?.get(it)?.collect = "true"
            mAdapter?.notifyItemChanged(it)
        })
        mViewModel?.mLgUncollectOriginId?.observe(viewLifecycleOwner, Observer {
            mAdapter?.data?.get(it)?.collect = "false"
            mAdapter?.notifyItemChanged(it)
        })
    }
}
