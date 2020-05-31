package com.ziqi.wanandroid.commonlibrary.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ziqi.wanandroid.commonlibrary.bean.User
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val _mLogin = MutableLiveData<User>()
    val mLogin: LiveData<User>
        get() = _mLogin


    fun login(username: String, password: String) {
        asyncExt(
            {
                _mLogin.value = withContext(Dispatchers.IO) {
                    NetRepository.login(HashMap<String, String>(4).also {
                        it.put("username", username)
                        it.put("password", password)
                    }).preProcessData()
                }
            },
            {
                zToast(errorInfo(it))
            }, true
        )
    }
}
