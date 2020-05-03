package com.ziqi.wanandroid.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.ziqi.baselibrary.ITimerManagerService
import com.ziqi.baselibrary.base.ZBaseFragment
import com.ziqi.baselibrary.common.WebInfo
import com.ziqi.baselibrary.view.viewpager2.BaseFragmentStateAdapter
import com.ziqi.wanandroid.R
import com.ziqi.wanandroid.databinding.FragmentMainBinding
import com.ziqi.wanandroid.ui.home.HomeFragment
import com.ziqi.wanandroid.ui.me.MeFragment
import com.ziqi.wanandroid.ui.project.ProjectFragment
import com.ziqi.wanandroid.ui.systematics.SystematicsFragment
import com.ziqi.wanandroid.view.MyPageTransformer
import com.ziqi.wanandroid.util.StartUtil
import kotlinx.android.synthetic.main.fragment_main.*


/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/8 11:59 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class MainFragment : ZBaseFragment<Parcelable, FragmentMainBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?): MainFragment {
            var mWBaseFragment = MainFragment()
            mWBaseFragment.arguments = bundle
            return mWBaseFragment
        }
    }

    private lateinit var serverConnection: ServiceConnection

    private var connected = false

    private var timerManagerService: ITimerManagerService? = null

    var viewModel: MainViewModel? = null

    val MIN_ALPHA = 0.5f

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.openWeb -> {
                handleOpenWeb()
            }
            R.id.llHome -> {
                handleBottomMenu(0)
            }
            R.id.llSystematics -> {
                handleBottomMenu(1)
            }
            R.id.llProject -> {
                handleBottomMenu(2)
            }
            R.id.llMe -> {
                handleBottomMenu(3)
            }
        }
    }

    override fun zVisibleToUser(isNewIntent: Boolean) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mViewDataBinding?.listener = this
        initServerConnection()
        bindServices()
        handleToobar()
        handleDrawer()
        handleBottomMenu(0)

        var mAdapter = object : BaseFragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 4
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> HomeFragment.newInstance(null)
                    1 -> SystematicsFragment.newInstance(null)
                    2 -> ProjectFragment.newInstance(null)
                    else -> MeFragment.newInstance(null)
                }
            }

        }
        mViewDataBinding?.viewPager2?.apply {
            adapter = mAdapter
            setPageTransformer(MyPageTransformer())
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handleBottomMenu(position)
                }
            })
        }
        mViewDataBinding?.viewPager2?.setUserInputEnabled(false); //true:滑动，false：禁止滑动
    }

    private fun handleBottomMenu(position: Int) {
        //设置这个
        viewPager2.setCurrentItem(position, false)
        handleBottomSelect(position)
    }

    private fun handleBottomSelect(position: Int) {
        mViewDataBinding?.apply {
            ivHome.isSelected = false
            ivSystematics.isSelected = false
            ivProject.isSelected = false
            ivMe.isSelected = false

            tvHome.isSelected = false
            tvSystematics.isSelected = false
            tvProject.isSelected = false
            tvMe.isSelected = false

        }
        when (position) {
            0 -> {
                mViewDataBinding?.apply {
                    ivHome.isSelected = true
                    tvHome.isSelected = true
                }
            }
            1 -> {
                mViewDataBinding?.apply {
                    ivSystematics.isSelected = true
                    tvSystematics.isSelected = true
                }
            }
            2 -> {
                mViewDataBinding?.apply {
                    ivProject.isSelected = true
                    tvProject.isSelected = true
                }
            }
            3 -> {
                mViewDataBinding?.apply {
                    ivMe.isSelected = true
                    tvMe.isSelected = true
                }
            }
        }
    }

    private fun handleOpenWeb() {
        activity?.let {
            val webInfo = WebInfo()
            webInfo.url = "https://www.wanandroid.com"
            StartUtil.startWebFragment(it, this, -1, webInfo)
        }
    }


    private fun handleDrawer() {
        /**
         * 参考这个链接
         * https://blog.csdn.net/gaoxiaoweiandy/article/details/81505914
         */
        var drawerToggle = ActionBarDrawerToggle(
            activity, drawerLayout, mToolBar,
            R.string.drawer_open,
            R.string.drawer_close
        );
        //同步drawerToggle按钮与侧滑菜单的打开（关闭）状态
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // 滑动的过程中执行 slideOffset：0~1
                var content = drawerLayout.getChildAt(0)

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
            it.itemId
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

    private fun handleToobar() {
        mTvTitle?.text = "玩安卓"
        mRightTwoMenu?.text = "测试"
        mRightTwoMenu?.visibility = View.VISIBLE
        mRightTwoMenu?.setOnClickListener {
            timerManagerService?.setTime("设置时间")
            zShowLoadDialog(-1, null)
            mToolBar?.postDelayed({
                zHideLoadDialog(-1)
            }, 1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serverConnection.also {
            activity?.unbindService(it)
        }
    }

    private fun bindServices() {
        var intent = Intent()
        intent.component =
            ComponentName("com.ziqi.timer", "com.ziqi.baselibrary.service.ServerTimerService")
        activity?.bindService(intent, serverConnection, Context.BIND_AUTO_CREATE)
    }


    private fun initServerConnection() {
        serverConnection = object : ServiceConnection {

            override fun onServiceDisconnected(name: ComponentName?) {
                connected = false
            }

            override fun onServiceConnected(name: ComponentName?, serviceBinder: IBinder?) {
                timerManagerService = ITimerManagerService.Stub.asInterface(serviceBinder)
                connected = true
            }
        }
    }


}