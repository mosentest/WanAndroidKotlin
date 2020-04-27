package com.ziqi.wanandroid.ui.main

import android.os.Parcelable
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ziqi.baselibrary.mvvm.BaseViewModel
import com.ziqi.baselibrary.mvvm.ViewModelActivity

class MainActivity : ViewModelActivity<MainViewModel, Parcelable, ViewDataBinding>() {

    override fun createFragment(): Fragment? {
        return MainFragment.newInstance(intent?.extras)
    }

    override fun onClick(p0: View?) {

    }

    override fun zVisibleToUser(isNewIntent: Boolean) {

    }


}
