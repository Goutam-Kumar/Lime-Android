package com.lime.android.service

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

    @POST("getgoodstype")
    suspend fun getGoods(@Body goodsTypesRequest: MODGoodsTypesRequest): Response<MODGoodsTypesResponse>

    @POST("getvehicles")
    suspend fun getVehicles(@Body vehiclesRequest: MODVehiclesRequest): Response<MODVehiclesResponse>
}
