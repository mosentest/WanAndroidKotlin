package com.ziqi.wanandroid.commonlibrary.ui.imagepreview

import android.view.View
import androidx.fragment.app.Fragment
import com.ziqi.wanandroid.commonlibrary.ui.common.CommonActivity

class ImagePreviewActivity : CommonActivity() {

    override fun createFragment(): Fragment? {
        return ImagePreviewFragment.newInstance(
            intent?.extras
        )
    }

    override fun onClick(v: View?) {

    }

    override fun zVisibleToUser(isNewIntent: Boolean) {

    }
}
