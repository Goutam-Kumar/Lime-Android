package com.lime.android.apprepository

import com.lime.android.models.login.MODLoginRequest
import com.lime.android.models.login.MODLoginResponse
import com.lime.android.models.register.MODRegisterResponse
import com.lime.android.networkhelper.ServiceResult

internal interface LimeRepository {
    suspend fun registerUser(): ServiceResult<MODRegisterResponse?>
    suspend fun loginUser(loginRequest: MODLoginRequest): ServiceResult<MODLoginResponse?>
}
