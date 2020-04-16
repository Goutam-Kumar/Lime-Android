package com.lime.android.fragments.additionaldetails

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lime.android.OfferBidDestination
import com.lime.android.TruckListDestination
import com.lime.android.apprepository.LimeRepository
import com.lime.android.apprepository.LimeRepositoryImpl
import com.lime.android.models.goods.GoodsType
import com.lime.android.models.goods.MODGoodsTypesRequest
import com.lime.android.models.goods.MODGoodsTypesResponse
import com.lime.android.models.vehicleandgoods.MODVehicleGoodsRequest
import com.lime.android.models.vehicleandgoods.MODVehicleGoodsResponse
import com.lime.android.models.vehicleandgoods.Vehicle
import com.lime.android.networkhelper.ServiceResult
import com.lime.android.sharedrepository.LimeSharedRepositoryImpl
import com.lime.android.ui.navigationui.NavigationViewModel
import com.lime.android.util.DISTANCE
import com.lime.android.util.VEHICLE_ID
import kotlinx.coroutines.launch

class AdditionalDetailsViewModel(private val arguments: Bundle, private val context: Context): NavigationViewModel() {
    private val limeRepository: LimeRepository = LimeRepositoryImpl()

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean> = _spinner
    private val _serviceException = MutableLiveData<String?>()
    val serviceException: LiveData<String?> = _serviceException

    private val _goodsType = MutableLiveData<List<GoodsType>>()
    val goodsType: LiveData<List<GoodsType>> = _goodsType
    private val vehicleId = arguments.getInt(VEHICLE_ID)
    private val distance = arguments.getFloat(DISTANCE)

    fun formattedDate(number: Int): String{
        return when {
            number < 10 -> "0".plus(number)
            number.toString().length == 4 -> number.toString().takeLast(2)
            else -> number.toString()
        }
    }

    fun onBidNowClicked() {
        navigateTo(OfferBidDestination())
    }

    fun getVehicleAndGoods() {
        viewModelScope.launch {
            showSpinner(true)
            when(val serviceResult =
                limeRepository.getGoods(getGoodsTypesRequest())){
                is ServiceResult.Success -> onSuccessOfGoods(serviceResult.data)
                is ServiceResult.Error -> onFailure(serviceResult.exception)
            }
        }
    }

    private fun onSuccessOfGoods(response: MODGoodsTypesResponse?) {
        showSpinner(false)
        response?.let {
            if (response.success == 1){
                _goodsType.value = response.goods_type
            }
        }
    }

    private fun getGoodsTypesRequest(): MODGoodsTypesRequest {
        return MODGoodsTypesRequest(
            user_id = LimeSharedRepositoryImpl(context).loggedInUser.user_id
        )
    }

    private fun onFailure(exception: String) {
        showSpinner(false)
        _serviceException.value = exception
    }

    private fun showSpinner(show: Boolean) {
        _spinner.value = show
    }

    fun onCheckFare() {
        navigateTo(TruckListDestination(vehicleId,distance))
    }
}
