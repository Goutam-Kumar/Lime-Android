package com.lime.android.apprepository

import com.lime.android.models.login.MODLoginRequest
import com.lime.android.models.login.MODLoginResponse
import com.lime.android.models.register.MODRegisterResponse
import com.lime.android.networkhelper.RetrofitCallbackHandler
import com.lime.android.networkhelper.ServiceResult
import com.lime.android.service.LimeRepositoryRetrofit
import com.lime.android.service.LimeRepositoryServices
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class LimeRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val rigsItService: LimeRepositoryServices = LimeRepositoryRetrofit().prepareService()
): LimeRepository {

    override suspend fun loginUser(loginRequest: MODLoginRequest): ServiceResult<MODLoginResponse?> {
        return withContext(ioDispatcher) {
            RetrofitCallbackHandler.processCall {
                rigsItService.loginUser(loginRequest)
            }
        }
    }

    override suspend fun registerUser(): ServiceResult<MODRegisterResponse?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
