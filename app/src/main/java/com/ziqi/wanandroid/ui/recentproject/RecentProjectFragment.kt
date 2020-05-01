package com.ziqi.wanandroid.ui.recentproject

import android.os.Parcelable
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ziqi.baselibrary.mvvm.ViewModelFragment
import com.ziqi.wanandroid.R
import com.ziqi.wanandroid.databinding.FragmentRecentBlogBinding
import com.ziqi.wanandroid.ui.common.BaseFragment
import com.ziqi.wanandroid.ui.recentblog.RecentBlogViewModel

class RecentProjectFragment :
    BaseFragment<RecentProjectViewModel, Parcelable, FragmentRecentBlogBinding>() {

    companion object {
        fun newInstance() = RecentProjectFragment()
    }

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
    }


    override fun onRefresh() {

    }

    override fun initView() {
    }

    override fun dealViewModel() {
    }


}
