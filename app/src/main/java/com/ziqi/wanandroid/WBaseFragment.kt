package com.ziqi.wanandroid

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import com.ziqi.baselibrary.ITimerManagerService
import com.ziqi.baselibrary.base.ZBaseFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/8 11:59 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class WBaseFragment : ZBaseFragment<Parcelable, ViewDataBinding>() {

    companion object {
        fun newInstance(bundle: Bundle?): WBaseFragment {
            var mWBaseFragment = WBaseFragment()
            mWBaseFragment.arguments = bundle
            return mWBaseFragment
        }
    }

    private lateinit var serverConnection: ServiceConnection

    private var connected = false

    private lateinit var timerManagerService: ITimerManagerService


    override fun zSetLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            btn.id -> {
                timerManagerService.setTime("设置时间")
            }
        }
    }

    override fun zVisibleToUser(isNewIntent: Boolean) {

        if (!isNewIntent) {
            initServerConnection()
            bindServices()
            btn.setOnClickListener(this)
            /**
             * 参考这个链接
             * https://blog.csdn.net/gaoxiaoweiandy/article/details/81505914
             */
            var drawerToggle = ActionBarDrawerToggle(
                activity, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close
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


    fun initServerConnection() {
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