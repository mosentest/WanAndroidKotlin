package com.ziqi.wanandroid.ui.imagepreview

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.animation.AlphaInAnimation
import com.ziqi.wanandroid.R
import com.ziqi.wanandroid.databinding.FragmentImagePreviewBinding
import com.ziqi.wanandroid.ui.common.BaseFragment
import com.ziqi.wanandroid.util.ImageLoad

class ImagePreviewFragment :
    BaseFragment<ImagePreviewViewModel, ImagePreviewParams, FragmentImagePreviewBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        fun newInstance(bundle: Bundle?) = ImagePreviewFragment().apply {
            arguments = bundle
        }
    }


    private lateinit var mAdapter: BaseQuickAdapter<String, BaseViewHolder>


    override fun zSetLayoutId(): Int {
        return R.layout.fragment_image_preview
    }

    override fun onClick(v: View?) {
    }

    override fun zVisibleToUser(isNewIntent: Boolean) {
        initView()
        dealViewModel()
        onRefresh()
    }

    override fun initView() {
        mAdapter = object : BaseQuickAdapter<String, BaseViewHolder>(
            R.layout.fragment_image_preview_item,
            null
        ) {
            override fun convert(helper: BaseViewHolder, item: String) {
                ImageLoad.loadUrl(
                    this@ImagePreviewFragment,
                    item,
                    helper.getView(R.id.iv)
                )
            }

        }
        mAdapter.openLoadAnimation(AlphaInAnimation())
        mViewDataBinding?.viewpager2?.adapter = mAdapter
        //=================================================================================
        mViewDataBinding?.viewpager2?.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        mViewDataBinding?.viewpager2?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mTvTitle?.text = """${position.plus(1)}/${mBundleData?.imgUrl?.size}"""
            }
        })
        mAdapter.setNewData(mBundleData?.imgUrl)
        mTvTitle?.text = """${mBundleData?.currentPos?.plus(1)}/${mBundleData?.imgUrl?.size}"""
        mViewDataBinding?.viewpager2?.setCurrentItem(mBundleData?.currentPos ?: 0, false)
    }


    override fun dealViewModel() {
    }

    override fun onRefresh() {

    }

}
