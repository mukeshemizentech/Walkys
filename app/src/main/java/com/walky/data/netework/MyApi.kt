package com.walky.data.netework

import com.walky.data.netework.responses.AuthResponse
import com.walky.utils.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {

    companion object {
        operator fun invoke(): MyApi {

            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }



    @FormUrlEncoded
    @POST(Constants.LoginApi)
    suspend fun userLogin(
        @Field("phone_number") phone : String
    ):Response<AuthResponse>
}