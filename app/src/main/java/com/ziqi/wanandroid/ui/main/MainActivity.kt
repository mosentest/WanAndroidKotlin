package com.ziqi.wanandroid.ui.main

import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.ziqi.wanandroid.commonlibrary.ui.common.CommonActivity

class MainActivity : CommonActivity<Parcelable>() {

    override fun createFragment(): Fragment? {
        return MainFragment.newInstance(intent?.extras)
    }

    override fun zEnableSwipe(): Boolean {
        return false
    }
}
