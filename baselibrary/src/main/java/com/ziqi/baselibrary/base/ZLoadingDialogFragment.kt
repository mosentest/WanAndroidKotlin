package com.ziqi.baselibrary.base

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.ziqi.baselibrary.base.interfaces.IView


/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/25 10:15 AM
 * Description:
 * History: 还是存在内存泄露...
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class ZLoadingDialogFragment : DialogFragment(), IView {

    companion object {
        fun newInstance(content: String?): ZLoadingDialogFragment {
            var fragment = ZLoadingDialogFragment()
            val bundle = Bundle()
            bundle.putString("content", content);
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val content = arguments?.getString("content")
        return activity?.let {
            val dialog = ProgressDialog(activity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setMessage(content)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog
        } ?: super.onCreateDialog(savedInstanceState)
    }

    override fun zGetClassName(): String {
        return javaClass.simpleName
    }
}