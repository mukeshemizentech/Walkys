package com.walky.walkys.ui.login

import android.view.View
import androidx.lifecycle.ViewModel
import com.walky.data.repository.UserRepository
import com.walky.utils.Coroutines


class AuthViewModel : ViewModel() {

    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null
    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        Coroutines.main {
            val response = UserRepository().userLogin("7737347444")
            if (response.isSuccessful) {
                if (response.body()?.status_code == 200)
                    authListener?.onSuccess(response.body()!!.data!!)
                else
                    authListener?.onFailure(response.message())
            } else {
                authListener?.onFailure("Error code is ${response.code()}")
            }
        }
    }
}