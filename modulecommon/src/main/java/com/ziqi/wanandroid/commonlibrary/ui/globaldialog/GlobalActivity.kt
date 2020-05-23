package com.ziqi.wanandroid.commonlibrary.ui.globaldialog

import android.os.Bundle
import com.ziqi.wanandroid.commonlibrary.ui.common.CommonActivity

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/4 7:00 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class GlobalActivity : CommonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置1像素
//        val window = window
//        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH)
//        window.setGravity(Gravity.START or Gravity.TOP)
//        val params = window.attributes
//        params.x = 0
//        params.y = 0
//        params.height = 1
//        params.width = 1
//        window.attributes = params
    }

    override fun zEnableSwipe(): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        //不需要这个
//        super.onBackPressed()
    }
}