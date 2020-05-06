package com.ziqi.wanandroid.commonlibrary.ui.globaldialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.DialogFragment
import com.ziqi.wanandroid.commonlibrary.ui.login.LoginParams
import com.ziqi.wanandroid.commonlibrary.util.StartUtil

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/5/4 7:03 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class LoginDialogFragment : DialogFragment() {
    companion object {
        fun newInstance(content: String?): LoginDialogFragment {
            var fragment = LoginDialogFragment()
            val bundle = Bundle()
            bundle.putString("content", content);
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val content = arguments?.getString("content")
        return activity?.let {
            val dialog = AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage(content)
                .setPositiveButton("我知道了", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        StartUtil.startLoginFragment(
                            it,
                            this@LoginDialogFragment,
                            -1,
                            LoginParams().apply {
                                isInvalid = true
                            })
                        it.finish()
                    }
                })
                .setCancelable(false)
                .create()
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog.setOnKeyListener { _, keyCode, _ ->
                //处理返回键的问题
                keyCode == KeyEvent.KEYCODE_BACK
            }
            dialog
        } ?: super.onCreateDialog(savedInstanceState)
    }
}