package com.ziqi.wanandroid.commonlibrary.ui.register

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ziqi.wanandroid.commonlibrary.bean.User
import com.ziqi.wanandroid.commonlibrary.net.NetRepository
import com.ziqi.wanandroid.commonlibrary.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterViewModel(ctx: Application) : BaseViewModel(ctx) {

    private val _mRegister = MutableLiveData<User>()
    val register: LiveData<User>
        get() = _mRegister


    fun register(username: String, password: String, repassword: String) {
        asyncExt(
            {
                _mRegister.value = withContext(Dispatchers.IO) {
                    // 3=x*0.75+1 基本是2倍所以写6好了
                    NetRepository.post<User>("user/register",
                        HashMap<String, String>(6).also {
                            it.put("username", username)
                            it.put("password", password)
                            it.put("repassword", repassword)
                        }).preProcessData()
                }
            },
            {
                zToast(errorInfo(it))
            }, true
        )
    }
}
