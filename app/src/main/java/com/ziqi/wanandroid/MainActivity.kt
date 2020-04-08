package com.ziqi.wanandroid

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.ziqi.baselibrary.ITimerManagerService
import com.ziqi.baselibrary.mvvm.ViewModelActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ViewModelActivity() {

    override fun createFragment(): Fragment? {
        return WBaseFragment.newInstance(intent.extras)
    }

    override fun onClick(p0: View?) {

    }

    override fun zVisibleToUser(isNewIntent: Boolean) {

    }


}
