package com.ziqi.wanandroid

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.ziqi.baselibrary.ITimerManagerService
import com.ziqi.baselibrary.base.BaseActivity
import com.ziqi.baselibrary.mvvm.ViewModelActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ViewModelActivity() {


    private lateinit var serverConnection: ServiceConnection

    private var connected = false

    private lateinit var timerManagerService: ITimerManagerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initServerConnection()
        bindServices()
        btn.setOnClickListener(this)
    }

    private fun bindServices() {
        var intent = Intent()
        intent.component =
            ComponentName("com.ziqi.timer", "com.ziqi.baselibrary.service.ServerTimerService")
        bindService(intent, serverConnection, Context.BIND_AUTO_CREATE)
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

    override fun onDestroy() {
        super.onDestroy()
        serverConnection.also {
            unbindService(it)
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            btn.id -> {
                timerManagerService.setTime("设置时间")
            }
        }
    }

    override fun zVisibleToUser(isNewIntent: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun zStatusLoadingView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun zStatusNetWorkView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun zStatusErrorView(type: Int, msg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun zStatusContentView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun zShowLoadingDialog(flag: Int, msg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun zHideLoadingDialog(flag: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
