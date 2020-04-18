package com.lime.android.apprepository

import com.lime.android.models.booking.MODBookingResponse
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
import com.lime.android.networkhelper.ServiceResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Part

internal interface LimeRepository {
    suspend fun registerUser(registerRequest: MODRegisterRequest): ServiceResult<MODRegisterResponse?>
    suspend fun loginUser(loginRequest: MODLoginRequest): ServiceResult<MODLoginResponse?>
    suspend fun updateFcm(request: MODFcmUpdateRequest): ServiceResult<MODFcmUpdateResponse?>
    suspend fun getVehicleList(vehicleTypesRequest: MODVehicleTypesRequest): ServiceResult<MODVehicleTypesResponse?>
    suspend fun getGoods(goodsTypesRequest: MODGoodsTypesRequest): ServiceResult<MODGoodsTypesResponse?>
    suspend fun getVehicles(vehiclesRequest: MODVehiclesRequest): ServiceResult<MODVehiclesResponse?>
    suspend fun bookNow(  auth:String,
                          pickup_address: RequestBody,
                          dropAddress: RequestBody,
                          bookingType: RequestBody,
                          userId: RequestBody,
                          fcmID: RequestBody,
                          pickupLat: RequestBody,
                          pickupLng: RequestBody,
                          dropLat: RequestBody,
                          dropLng: RequestBody,
                          email: RequestBody,
                          cName: RequestBody,
                          address: RequestBody,
                          natID: RequestBody,
                          noPerson: RequestBody,
                          vehicleTypeId: RequestBody,
                          certificate: MultipartBody.Part?,
                          bill: MultipartBody.Part?
    ) : ServiceResult<MODBookingResponse?>
}
