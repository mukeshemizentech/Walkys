package com.walky.walkys.ui.login

import com.walky.data.netework.responses.Data


interface AuthListener {
    fun onStarted()
    fun onSuccess(loginResponse: Data)
    fun onFailure(message: String);
}