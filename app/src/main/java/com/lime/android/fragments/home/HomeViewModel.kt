package com.lime.android.fragments.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lime.android.AdditionalDetailsDestination
import com.lime.android.R
import com.lime.android.TruckListDestination
import com.lime.android.apprepository.LimeRepository
import com.lime.android.apprepository.LimeRepositoryImpl
import com.lime.android.datarepository.DataHolder
import com.lime.android.datarepository.LimeBookingInformation
import com.lime.android.models.vehicleTypes.MODVehicleTypesRequest
import com.lime.android.models.vehicleTypes.MODVehicleTypesResponse
import com.lime.android.models.vehicleTypes.VehicleType
import com.lime.android.networkhelper.ServiceResult
import com.lime.android.sharedrepository.LimeSharedRepositoryImpl
import com.lime.android.ui.navigationui.NavigationViewModel
import com.lime.android.util.GLOBAL_TAG
import com.lime.android.util.LimeUtils
import kotlinx.coroutines.launch
import kotlin.math.round

class HomeViewModel(private val context: Context): NavigationViewModel() {
    private val limeRepository: LimeRepository = LimeRepositoryImpl()

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean> = _spinner
    private val _serviceException = MutableLiveData<String?>()
    val serviceException: LiveData<String?> = _serviceException
    private val _vehicleTypes = MutableLiveData<List<VehicleType>?>()
    val vehicleTypes:LiveData<List<VehicleType>?> = _vehicleTypes

    var selectedVehicleId: Int = 0
    var pickupLat: Double = 0.0
    var pickupLng: Double = 0.0
    var dropLat: Double = 0.0
    var dropLng: Double = 0.0
    var pickUpAddress: String? = null
    var dropAddress: String? = null

    fun onVehicleClicked(position: Int, vehicleId: Int) {
        Log.d(GLOBAL_TAG,vehicleId.toString())
        selectedVehicleId = vehicleId
        //navigateTo(TruckListDestination())
    }

    fun onContinueClicked() {
        validateAllReqData()
    }

    private fun validateAllReqData() {
        if (validateAllData()){
            val distance = round(LimeUtils.getDistanceInKm(pickupLat,pickupLng,dropLat,dropLng))
            val limeBookingInformation = LimeBookingInformation(
                pickUpLat = pickupLat,
                pickUpLng = pickupLng,
                dropLat = dropLat,
                dropLng = dropLng,
                pickUpAddress = pickUpAddress,
                dropAddress = dropAddress,
                distance = distance,
                vehicleTypeId = selectedVehicleId
            )
            val dataHolder = DataHolder.build(limeBookingInformation)
            navigateTo(AdditionalDetailsDestination(selectedVehicleId,distance, dataHolder))
        }
    }

    private fun validateAllData(): Boolean {
        var isValid: Boolean = true
        when {
            selectedVehicleId == 0 -> {
                isValid = false
                _serviceException.value = context.getString(R.string.select_vehicle_type)
            }
            pickupLat == 0.0 -> {
                isValid = false
                _serviceException.value = context.getString(R.string.select_pickup)
            }
            dropLat == 0.0 -> {
                isValid = false
                _serviceException.value = context.getString(R.string.select_drop)
            }
        }
        return isValid
    }

    fun getVehicleCategories() {
        viewModelScope.launch {
            showSpinner(true)
            when(val serviceResult =
                limeRepository.getVehicleList(getVehicleTypesRequest())){
                is ServiceResult.Success -> onSuccessOfVehicleTypes(serviceResult.data)
                is ServiceResult.Error -> onFailure(serviceResult.exception)
            }
        }
    }

    private fun onFailure(exception: String) {
        showSpinner(false)
        _serviceException.value = exception
    }

    private fun onSuccessOfVehicleTypes(response: MODVehicleTypesResponse?) {
        showSpinner(false)
        response?.let {
            if (response.success == 1){
                _vehicleTypes.value = response.vehicle_types
            }
        }
    }

    private fun getVehicleTypesRequest(): MODVehicleTypesRequest {
        return MODVehicleTypesRequest(
            user_id = LimeSharedRepositoryImpl(context).loggedInUser.user_id
        )
    }

    private fun showSpinner(show: Boolean) {
        _spinner.value = show
    }
}
