package com.ziqi.wanandroid.ui.main

import androidx.fragment.app.Fragment
import com.ziqi.wanandroid.commonlibrary.ui.common.CommonActivity

class MainActivity : CommonActivity() {

    override fun createFragment(): Fragment? {
        return MainFragment.newInstance(intent?.extras)
    }


}
