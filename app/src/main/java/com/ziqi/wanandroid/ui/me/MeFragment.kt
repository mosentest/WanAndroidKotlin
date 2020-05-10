package com.ziqi.wanandroid.ui.me

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.github.florent37.fiftyshadesof.FiftyShadesOf
import com.ziqi.baselibrary.common.WebInfo
import com.ziqi.baselibrary.livedata.Event
import com.ziqi.baselibrary.util.ContextUtils
import com.ziqi.wanandroid.R
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseFragment
import com.ziqi.wanandroid.commonlibrary.ui.globaldialog.GlobalActivity
import com.ziqi.wanandroid.commonlibrary.ui.globaldialog.GlobalParams
import com.ziqi.wanandroid.commonlibrary.util.LoginManager
import com.ziqi.wanandroid.commonlibrary.util.StartUtil
import com.ziqi.wanandroid.databinding.FragmentMeBinding

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/15 11:37 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class MeFragment : BaseFragment<MeViewModel, Parcelable, FragmentMeBinding>() {


    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?): MeFragment {
            var mWBaseFragment = MeFragment()
            mWBaseFragment.arguments = bundle
            return mWBaseFragment
        }
    }


    private var mFiftyShadesOf: FiftyShadesOf? = null

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.openWeb -> {
                handleOpenWeb()
            }
            R.id.tvNoLogin -> {
                toLogin(object : LoginListener {
                    override fun onSuccess() {
                        zToastShort(-1, "登录成功")
                    }

                    override fun onCancel() {
                    }

                }, null)
            }
            R.id.tvSearch -> {
            }
            R.id.tvWxArticle -> {
                activity?.apply {
                    StartUtil.startWxArticleFragment(this, this@MeFragment, -1, null)
                }
            }
            R.id.tvCollect -> {
                if (LoginManager.isNoLogin()) {
                    toLogin(object : LoginListener {
                        override fun onSuccess() {
                            zToastShort(-1, "登录成功")
                        }

                        override fun onCancel() {
                        }

                    }, null)
                } else {

                }
            }
            R.id.tvUserArticle -> {

            }
            R.id.tvWenda -> {

            }
            R.id.tvLoginInvalid -> {
                activity?.apply {
                    mViewModel?.mToLogin?.value = Event(true)
                }
            }
            R.id.tvSerialDialog -> {
                mViewModel?.serialDialog()
            }
            R.id.tvParallelDialog -> {
                mViewModel?.parallelDialog()
            }
        }
    }

    override fun zVisibleToUser(isNewIntent: Boolean) {

    }

    override fun zLazyVisible() {
        super.zLazyVisible()
        mViewDataBinding?.listener = this
        handleToolBar()
        handleDrawer()
        mFiftyShadesOf = FiftyShadesOf.with(context)
            .on(
                R.id.tvNoLogin,
                R.id.llLogin,
                R.id.tvSearch,
                R.id.tvWxArticle,
                R.id.tvCollect,
                R.id.tvUserArticle,
                R.id.tvWenda,
                R.id.tvLoginInvalid,
                R.id.tvSerialDialog,
                R.id.tvParallelDialog,
                R.id.tvLogout
            )
            .start()
        mViewDataBinding?.tvNoLogin?.postDelayed({
            mFiftyShadesOf?.stop()
        }, 100)
    }

    override fun onResume() {
        super.onResume()
        mViewDataBinding?.tvNoLogin?.visibility =
            if (LoginManager.isNoLogin()) View.VISIBLE else View.GONE
        mViewDataBinding?.llLogin?.visibility =
            if (LoginManager.isLogin()) View.VISIBLE else View.GONE
        mViewDataBinding?.tvLogout?.visibility =
            if (LoginManager.isLogin()) View.VISIBLE else View.GONE
    }

    override fun onRefresh() {

    }


    override fun initView() {

    }

    override fun dealViewModel() {

    }

    private fun handleToolBar() {
        mTvTitle?.text = "我的"
        mRightTwoMenu?.text = "搜索"
        mRightTwoMenu?.visibility = View.VISIBLE
        mRightTwoMenu?.setOnClickListener {
            zShowLoadDialog(-1, null)
            mToolBar?.postDelayed({
                zHideLoadDialog(-1)
            }, 1000)
        }
    }


    private fun handleDrawer() {
        /**
         * 参考这个链接
         * https://blog.csdn.net/gaoxiaoweiandy/article/details/81505914
         */
        var drawerToggle = ActionBarDrawerToggle(
            activity, mViewDataBinding?.drawerLayout, mToolBar,
            R.string.drawer_open,
            R.string.drawer_close
        );
        //同步drawerToggle按钮与侧滑菜单的打开（关闭）状态
        drawerToggle.syncState();
        mViewDataBinding?.drawerLayout?.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // 滑动的过程中执行 slideOffset：0~1
                var content = mViewDataBinding?.drawerLayout!!.getChildAt(0)

                var scale = 1 - slideOffset//1~0
                var leftScale = (1 - 0.3 * scale)
                var rightScale = (0.7f + 0.3 * scale)//0.7~1

                drawerView.setScaleX(leftScale.toFloat())//1~0.7
                drawerView.setScaleY(leftScale.toFloat())//1~0.7

                content.setScaleX(rightScale.toFloat())
                content.setScaleY(rightScale.toFloat())
                content.setTranslationX(drawerView.getMeasuredWidth() * slideOffset)//0~width
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }
        })
        //https://www.jianshu.com/p/4b33d0a715e6
        mViewDataBinding?.navigationView?.itemIconTintList = null
        mViewDataBinding?.navigationView?.setNavigationItemSelectedListener {
            when (it.itemId) {

            }
            true
        }
        mViewDataBinding
            ?.navigationView
            ?.getHeaderView(0)
            ?.findViewById<Button>(R.id.openWeb)
            ?.setOnClickListener {
                handleOpenWeb()
            }
    }

    private fun handleOpenWeb() {
        activity?.let {
            val webInfo = WebInfo()
            webInfo.url = "https://www.wanandroid.com"
            StartUtil.startWebFragment(it, this, -1, webInfo)
        }
    }

}