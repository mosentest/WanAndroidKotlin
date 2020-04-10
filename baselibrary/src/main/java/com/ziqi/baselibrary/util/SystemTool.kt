package com.ziqi.baselibrary.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.widget.Toast


/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/10 9:59 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object SystemTool {

    //没有网络
    public const val NETWORK_NONE = 1
    //移动网络
    public const val NETWORK_MOBILE = 0
    //无线网络
    public const val NETWORW_WIFI = 2

    //获取网络启动
    fun checkNet(context: Context?): Int {
        //连接服务 CONNECTIVITY_SERVICE
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //网络信息 NetworkInfo
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) { //判断是否是wifi
            if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) { //返回无线网络
                return NETWORW_WIFI
                //判断是否移动网络
            } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                //返回移动网络
                return NETWORK_MOBILE
            }
        } else { //没有网络
            return NETWORK_NONE
        }
        //默认返回  没有网络
        return NETWORK_NONE
    }
}