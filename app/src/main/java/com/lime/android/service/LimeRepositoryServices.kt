package com.lime.android.service

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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LimeRepositoryServices {

    @POST("user-registration")
    suspend fun registerUser(@Body request: MODRegisterRequest): Response<MODRegisterResponse?>

    @POST("user-login")
    suspend fun loginUser(@Body request: MODLoginRequest): Response<MODLoginResponse?>

    @POST("user-fcm-update")
    suspend fun fcmUpdate(@Body request: MODFcmUpdateRequest): Response<MODFcmUpdateResponse?>

    @POST("getvehicletypes")
    suspend fun getVehicleTypes(@Body vehicleTypesRequest: MODVehicleTypesRequest): Response<MODVehicleTypesResponse?>

    @POST("getvehicles")
    suspend fun getVehicleAndGoods(@Body vehicleGoodsRequest: MODVehicleGoodsRequest): Response<MODVehicleGoodsResponse>

}
