package com.ziqi.wanandroid.ui.imagepreview

import android.os.Parcelable
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ziqi.baselibrary.base.ZBaseActivity
import com.ziqi.baselibrary.common.WebFragment

class ImagePreviewActivity : ZBaseActivity<Parcelable, ViewDataBinding>() {

    override fun createFragment(): Fragment? {
        return ImagePreviewFragment.newInstance(intent?.extras)
    }

    override fun onClick(v: View?) {

    }

    override fun zVisibleToUser(isNewIntent: Boolean) {

    }
}
