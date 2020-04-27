package com.lime.android.service

import com.lime.android.models.booking.MODBookingResponse
import com.lime.android.models.goods.MODGoodsTypesRequest
import com.lime.android.models.goods.MODGoodsTypesResponse
import com.lime.android.models.login.MODLoginRequest
import com.lime.android.models.login.MODLoginResponse
import com.lime.android.models.main.MODFcmUpdateRequest
import com.lime.android.models.main.MODFcmUpdateResponse
import com.lime.android.models.orderhistory.MODCurrentOrderRequest
import com.lime.android.models.orderhistory.MODCurrentOrderResponse
import com.lime.android.models.register.MODRegisterRequest
import com.lime.android.models.register.MODRegisterResponse
import com.lime.android.models.vehicleTypes.MODVehicleTypesRequest
import com.lime.android.models.vehicleTypes.MODVehicleTypesResponse
import com.lime.android.models.vehicleandgoods.MODVehicleGoodsRequest
import com.lime.android.models.vehicleandgoods.MODVehicleGoodsResponse
import com.lime.android.models.vehicles.MODVehiclesRequest
import com.lime.android.models.vehicles.MODVehiclesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

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
    suspend fun getGoods(@Body goodsTypesRequest: MODGoodsTypesRequest): Response<MODGoodsTypesResponse?>

    @POST("getvehicles")
    suspend fun getVehicles(@Body vehiclesRequest: MODVehiclesRequest): Response<MODVehiclesResponse?>

    @Multipart
    @POST("book-now")
    suspend fun bookNow(@Header("Authorization")  auth:String,
                        @Part("source_address")  pickup_address: RequestBody,
                        @Part("dest_address") dropAddress: RequestBody,
                        @Part("booking_type") bookingType: RequestBody,
                        @Part("user_id") userId: RequestBody,
                        @Part("fcm_id") fcmID: RequestBody,
                        @Part("source_lat") pickupLat: RequestBody,
                        @Part("source_long") pickupLng: RequestBody,
                        @Part("dest_lat") dropLat: RequestBody,
                        @Part("dest_long") dropLng: RequestBody,
                        @Part("name") email: RequestBody,
                        @Part("contact_name") cName: RequestBody,
                        @Part("address") address: RequestBody,
                        @Part("national_id") natID: RequestBody,
                        @Part("no_of_persons") noPerson: RequestBody,
                        @Part("vehicle_typeid") vehicleTypeId: RequestBody,
                        @Part("pickup_date") pickUpDate: RequestBody,
                        @Part("total_amount") totalAmount: RequestBody,
                        @Part certificate: MultipartBody.Part?,
                        @Part bill: MultipartBody.Part?
    ): Response<MODBookingResponse?>

    @POST("ride-history")
    suspend fun getCurrentOrders(@Body custId: MODCurrentOrderRequest, @Header("Authorization") apiToken: String): Response<MODCurrentOrderResponse?>
}
