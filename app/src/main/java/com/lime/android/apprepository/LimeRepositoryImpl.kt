package com.lime.android.apprepository

import com.lime.android.models.goods.MODGoodsTypesRequest
import com.lime.android.models.goods.MODGoodsTypesResponse
import com.lime.android.models.login.MODLoginRequest
import com.lime.android.models.login.MODLoginResponse
import com.lime.android.models.main.MODFcmUpdateRequest
import com.lime.android.models.main.MODFcmUpdateResponse
import com.lime.android.models.register.MODRegisterRequest
import com.lime.android.models.register.MODRegisterResponse
import com.lime.android.models.vehicleTypes.MODVehicleTypesRequest
import com.lime.android.models.vehicleTypes.MODVehicleTypesResponse
import com.lime.android.models.vehicleandgoods.MODVehicleGoodsRequest
import com.lime.android.models.vehicleandgoods.MODVehicleGoodsResponse
import com.lime.android.models.vehicles.MODVehiclesRequest
import com.lime.android.models.vehicles.MODVehiclesResponse
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
    override suspend fun getVehicles(vehiclesRequest: MODVehiclesRequest): ServiceResult<MODVehiclesResponse?> {
        return withContext(ioDispatcher) {
            RetrofitCallbackHandler.processCall {
                rigsItService.getVehicles(vehiclesRequest)
            }
        }
    }

    override suspend fun getGoods(goodsTypesRequest: MODGoodsTypesRequest): ServiceResult<MODGoodsTypesResponse?> {
        return withContext(ioDispatcher) {
            RetrofitCallbackHandler.processCall {
                rigsItService.getGoods(goodsTypesRequest)
            }
        }
    }

    override suspend fun getVehicleList(vehicleTypesRequest: MODVehicleTypesRequest): ServiceResult<MODVehicleTypesResponse?> {
        return withContext(ioDispatcher) {
            RetrofitCallbackHandler.processCall {
                rigsItService.getVehicleTypes(vehicleTypesRequest)
            }
        }
    }

    override suspend fun updateFcm(request: MODFcmUpdateRequest): ServiceResult<MODFcmUpdateResponse?> {
        return withContext(ioDispatcher) {
            RetrofitCallbackHandler.processCall {
                rigsItService.fcmUpdate(request)
            }
        }
    }

    override suspend fun registerUser(registerRequest: MODRegisterRequest): ServiceResult<MODRegisterResponse?> {
        return withContext(ioDispatcher) {
            RetrofitCallbackHandler.processCall {
                rigsItService.registerUser(registerRequest)
            }
        }
    }

    override suspend fun loginUser(loginRequest: MODLoginRequest): ServiceResult<MODLoginResponse?> {
        return withContext(ioDispatcher) {
            RetrofitCallbackHandler.processCall {
                rigsItService.loginUser(loginRequest)
            }
        }
    }
}
