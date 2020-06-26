package com.ziqi.wanandroid.me.ui.collect

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.ziqi.baselibrary.view.status.ZStatusViewBuilder
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseFragment
import com.ziqi.wanandroid.me.R
import com.ziqi.wanandroid.me.databinding.FragmentCollectBinding

class CollectFragment : BaseFragment<CollectViewModel, Parcelable, FragmentCollectBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?) = CollectFragment().apply {
            arguments = bundle
        }
    }

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_collect
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

    override fun initView() {

    }

    override fun dealViewModel() {

    }

    override fun onClick(v: View?) {

    }

    override fun onRefresh() {

    }
}
