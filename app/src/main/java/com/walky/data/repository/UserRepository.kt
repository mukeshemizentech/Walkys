package com.walky.data.repository

import com.walky.data.netework.MyApi
import com.walky.data.netework.responses.AuthResponse
import retrofit2.Response


class UserRepository  {


    suspend fun userLogin(phone_number : String) : Response<AuthResponse> {
        return MyApi().userLogin(phone_number)
    }
}