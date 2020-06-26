package com.ziqi.wanandroid.me.ui.wxarticle

import android.os.Bundle
import android.os.Parcelable
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.ziqi.baselibrary.util.StartActivityCompat
import com.ziqi.baselibrary.view.status.ZStatusViewBuilder
import com.ziqi.baselibrary.view.viewpager2.BaseFragmentStateAdapter
import com.ziqi.wanandroid.commonlibrary.bean.Tree
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseFragment
import com.ziqi.wanandroid.me.R
import com.ziqi.wanandroid.me.databinding.FragmentWxArticleBinding
import com.ziqi.wanandroid.me.ui.wxarticlelist.WxArticleListFragment
import com.ziqi.wanandroid.me.ui.wxarticlelist.WxArticleListParams

class WxArticleFragment :
    BaseFragment<WxArticleViewModel, Parcelable, FragmentWxArticleBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?) = WxArticleFragment().apply {
            arguments = bundle
        }
    }

    private var mTree: MutableList<Tree>? = null

    private var mTitles = mutableListOf<String>()

    private var mTabLayoutMediator: TabLayoutMediator? = null

    private var mAdapter: BaseFragmentStateAdapter? = null

    override fun zContentViewId(): Int {
        return R.id.myRootView
    }

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_wx_article
    }

    override fun onClick(v: View?) {

    }

    override fun zVisibleToUser(isNewIntent: Boolean) {
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
        mViewModel?.loadTree()
    }

    override fun initView() {
        mAdapter = object : BaseFragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return mTitles.size
            }

            override fun createFragment(position: Int): Fragment {
                return WxArticleListFragment.newInstance(
                    Bundle().apply {
                        putParcelable(
                            StartActivityCompat.NEXT_PARCELABLE,
                            WxArticleListParams().apply {
                                this.tree = mTree?.get(position)
                            })
                    }
                )
            }
        }
        mViewDataBinding?.viewPager2?.apply {
            adapter = mAdapter
        }
        mViewDataBinding?.tabLayout?.apply {
            this.isTabIndicatorFullWidth = false
        }
        mViewDataBinding?.viewPager2?.isUserInputEnabled = false; //true:滑动，false：禁止滑动
        if (mViewDataBinding?.tabLayout != null && mViewDataBinding?.viewPager2 != null) {
            mTabLayoutMediator = TabLayoutMediator(
                mViewDataBinding?.tabLayout!!,
                mViewDataBinding?.viewPager2!!,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                    tab.text = Html.fromHtml(mTitles[position])
                })
        }
        mTabLayoutMediator?.attach()
    }

    override fun dealViewModel() {
        mViewModel?.mTree?.observe(viewLifecycleOwner, Observer {
            it?.apply {
                mTree = this
                mTitles.clear()
                for (tree in this) {
                    mTitles.add(tree.name)
                }
                if (mTitles.isNotEmpty()) {
                    mAdapter?.notifyDataSetChanged()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabLayoutMediator?.detach()
    }

}
