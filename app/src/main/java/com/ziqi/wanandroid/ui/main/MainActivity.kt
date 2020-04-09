package com.ziqi.wanandroid.ui.main

import android.view.View
import androidx.fragment.app.Fragment
import com.ziqi.baselibrary.mvvm.ViewModelActivity

class MainActivity : ViewModelActivity() {

    override fun createFragment(): Fragment? {
        return MainFragment.newInstance(intent.extras)
    }

    override fun onClick(p0: View?) {

    }

    override fun zVisibleToUser(isNewIntent: Boolean) {

    }


}
