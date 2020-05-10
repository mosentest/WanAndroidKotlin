package com.ziqi.baselibrary.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import com.ziqi.baselibrary.ITimerManagerService
import com.ziqi.baselibrary.MyTime
import com.ziqi.baselibrary.util.MyHandler

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/3 6:02 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class ServerTimerService : Service() {

    private val TAG = ServerTimerService::class.java.simpleName


    private var myHandler: MyHandler? = null
    private val timerManagerServiceStub: ITimerManagerService.Stub =
        object : ITimerManagerService.Stub() {

            /**
             * 这些方法说明是在子线程
             */
            @Throws(RemoteException::class)
            override fun getTime(dataFormat: String?, time: MyTime?) {
                Log.i(TAG, dataFormat)
            }

            @Throws(RemoteException::class)
            override fun setTime(time: String?): Boolean {
                myHandler?.post {
                    Toast.makeText(this@ServerTimerService, time, Toast.LENGTH_SHORT).show()
                }
                Log.i(TAG, time)
                return false
            }

            @Throws(RemoteException::class)
            override fun start() {
                Log.i(TAG, "start")
            }

            @Throws(RemoteException::class)
            override fun stop() {
                Log.i(TAG, "stop")
            }

        }

    override fun onCreate() {
        super.onCreate()
        myHandler = MyHandler(null)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return timerManagerServiceStub
    }

    override fun onDestroy() {
        super.onDestroy()
        myHandler?.removeCallbacksAndMessages(null)
    }
}