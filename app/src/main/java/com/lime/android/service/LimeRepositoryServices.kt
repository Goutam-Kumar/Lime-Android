package com.lime.android.service

import com.lime.android.models.login.MODLoginRequest
import com.lime.android.models.login.MODLoginResponse
import com.lime.android.models.register.MODRegisterRequest
import com.lime.android.models.register.MODRegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LimeRepositoryServices {

    @POST("user-registration")
    suspend fun registerUser(@Body request: MODRegisterRequest): Response<MODRegisterResponse?>

    @POST("user-login")
    suspend fun loginUser(@Body request: MODLoginRequest): Response<MODLoginResponse?>

}
