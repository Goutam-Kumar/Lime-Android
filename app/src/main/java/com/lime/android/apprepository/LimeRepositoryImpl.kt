package com.lime.android.apprepository

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
import com.lime.android.networkhelper.RetrofitCallbackHandler
import com.lime.android.networkhelper.ServiceResult
import com.lime.android.service.LimeRepositoryRetrofit
import com.lime.android.service.LimeRepositoryServices
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

internal class LimeRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val rigsItService: LimeRepositoryServices = LimeRepositoryRetrofit().prepareService()
): LimeRepository {

    override suspend fun bidNow(
        auth: String,
        dropAddress: RequestBody,
        dropLng: RequestBody,
        dropLat: RequestBody,
        pickup_address: RequestBody,
        pickupLat: RequestBody,
        pickupLng: RequestBody,
        address: RequestBody,
        bookingType: RequestBody,
        cName: RequestBody,
        email: RequestBody,
        fcmID: RequestBody,
        natID: RequestBody,
        noPerson: RequestBody,
        userId: RequestBody,
        vehicleTypeId: RequestBody,
        pickUpDate: RequestBody,
        bidAmount: RequestBody,
        bill: MultipartBody.Part?,
        certificate: MultipartBody.Part?
    ): ServiceResult<MODBookingResponse?> {
        return withContext(ioDispatcher) {
            RetrofitCallbackHandler.processCall {
                rigsItService.bidNow(
                    auth,
                    pickup_address,
                    dropAddress,
                    bookingType,
                    userId,
                    fcmID,
                    pickupLat,
                    pickupLng,
                    dropLat,
                    dropLng,
                    email,
                    cName,
                    address,
                    natID,
                    noPerson,
                    vehicleTypeId,
                    pickUpDate,
                    bidAmount,
                    certificate,
                    bill
                )
            }
        }
    }

    override suspend fun getCurrentOrder(
        custId: MODCurrentOrderRequest,
        apiToken: String
    ): ServiceResult<MODCurrentOrderResponse?> {
        return withContext(ioDispatcher) {
            RetrofitCallbackHandler.processCall {
                rigsItService.getCurrentOrders(custId,apiToken)
            }
        }
    }

    override suspend fun bookNow(
        auth: String,
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
        pickUpDate: RequestBody,
        totalAmount: RequestBody,
        certificate: MultipartBody.Part?,
        bill: MultipartBody.Part?
    ): ServiceResult<MODBookingResponse?> {
        return withContext(ioDispatcher) {
            RetrofitCallbackHandler.processCall {
                rigsItService.bookNow(
                    auth,
                    pickup_address,
                    dropAddress,
                    bookingType,
                    userId,
                    fcmID,
                    pickupLat,
                    pickupLng,
                    dropLat,
                    dropLng,
                    email,
                    cName,
                    address,
                    natID,
                    noPerson,
                    vehicleTypeId,
                    pickUpDate,
                    totalAmount,
                    certificate,
                    bill
                )
            }
        }
    }

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
