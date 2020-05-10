package com.ziqi.wanandroid.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ziqi.baselibrary.ITimerManagerService
import com.ziqi.baselibrary.util.statusbar.StatusBarUtil
import com.ziqi.baselibrary.view.viewpager2.BaseFragmentStateAdapter
import com.ziqi.wanandroid.R
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseFragment
import com.ziqi.wanandroid.databinding.FragmentMainBinding
import com.ziqi.wanandroid.ui.home.HomeFragment
import com.ziqi.wanandroid.ui.me.MeFragment
import com.ziqi.wanandroid.ui.project.ProjectFragment
import com.ziqi.wanandroid.ui.systematics.SystematicsFragment
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
class MainFragment : BaseFragment<MainViewModel, Parcelable, FragmentMainBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?): MainFragment {
            var mWBaseFragment = MainFragment()
            mWBaseFragment.arguments = bundle
            return mWBaseFragment
        }
    }

    private lateinit var mFragmentAdapter: BaseFragmentStateAdapter

    private lateinit var serverConnection: ServiceConnection

    private var connected = false

    private var timerManagerService: ITimerManagerService? = null

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
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
        mViewDataBinding?.listener = this
        initServerConnection()
        bindServices()
        handleBottomMenu(0)
        mFragmentAdapter = object : BaseFragmentStateAdapter(this) {
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
            adapter = mFragmentAdapter
            //setPageTransformer(MyPageTransformer())
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handleBottomMenu(position)
                }
            })
        }
        mViewDataBinding?.viewPager2?.isUserInputEnabled = false; //true:滑动，false：禁止滑动
        mViewDataBinding?.viewPager2?.offscreenPageLimit = 4
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
                activity?.apply {
                    StatusBarUtil.setTranslucentStatus(this)
                    StatusBarUtil.setStatusBarDarkTheme(this, true)
                }
            }
            1 -> {
                mViewDataBinding?.apply {
                    ivSystematics.isSelected = true
                    tvSystematics.isSelected = true
                }
                activity?.apply {
                    StatusBarUtil.setStatusBarColor(
                        this,
                        resources.getColor(R.color.color_FFFFFF)
                    )
                    StatusBarUtil.setStatusBarDarkTheme(this, true)
                }
            }
            2 -> {
                mViewDataBinding?.apply {
                    ivProject.isSelected = true
                    tvProject.isSelected = true
                }
                activity?.apply {
                    StatusBarUtil.setStatusBarColor(
                        this,
                        resources.getColor(R.color.color_FFFFFF)
                    )
                    StatusBarUtil.setStatusBarDarkTheme(this, true)
                }
            }
            3 -> {
                mViewDataBinding?.apply {
                    ivMe.isSelected = true
                    tvMe.isSelected = true
                }
                activity?.apply {
                    StatusBarUtil.setTranslucentStatus(this)
                    StatusBarUtil.setStatusBarDarkTheme(this, false)
                }
            }
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

    override fun initView() {

    }

    override fun dealViewModel() {

    }

    override fun onRefresh() {

    }

}