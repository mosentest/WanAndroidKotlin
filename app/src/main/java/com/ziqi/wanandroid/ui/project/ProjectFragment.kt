package com.ziqi.wanandroid.ui.project

import android.os.Bundle
import android.os.Parcelable
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.ziqi.baselibrary.view.status.ZStatusViewBuilder
import com.ziqi.baselibrary.view.viewpager2.BaseFragmentStateAdapter
import com.ziqi.wanandroid.R
import com.ziqi.wanandroid.bean.Tree
import com.ziqi.wanandroid.databinding.FragmentProjectBinding
import com.ziqi.wanandroid.ui.common.BaseFragment
import com.ziqi.wanandroid.ui.projectlist.ProjectListFragment
import com.ziqi.wanandroid.ui.recentblog.RecentBlogFragment
import com.ziqi.wanandroid.ui.recentproject.RecentProjectFragment

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/15 11:37 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class ProjectFragment :
    BaseFragment<ProjectViewModel, Parcelable, FragmentProjectBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?): ProjectFragment {
            var mWBaseFragment = ProjectFragment()
            mWBaseFragment.arguments = bundle
            return mWBaseFragment
        }
    }

    private var mTree: MutableList<Tree>? = null

    private var mTitles = mutableListOf<String>()

    private var mTabLayoutMediator: TabLayoutMediator? = null

    private var mAdapter: BaseFragmentStateAdapter? = null

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_project
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
        mViewModel?.loadTree()
    }

    override fun initView() {
        mAdapter = object : BaseFragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return mTitles.size
            }

            override fun createFragment(position: Int): Fragment {
                return ProjectListFragment.newInstance(
                    Bundle().apply {
                        putParcelable("tree", mTree?.get(position))
                    }
                )
            }
        }
        mViewDataBinding?.viewPager2?.apply {
            adapter = mAdapter
        }
        mViewDataBinding?.tabLayout?.apply {
            this.isTabIndicatorFullWidth = true
        }
        //mViewDataBinding?.viewPager2?.isUserInputEnabled = false; //true:滑动，false：禁止滑动
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