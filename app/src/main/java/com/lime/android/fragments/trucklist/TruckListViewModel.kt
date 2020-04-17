package com.lime.android.fragments.trucklist

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lime.android.OrderDetailsDestination
import com.lime.android.R
import com.lime.android.TruckListDestination
import com.lime.android.apprepository.LimeRepository
import com.lime.android.apprepository.LimeRepositoryImpl
import com.lime.android.datarepository.DataHolder
import com.lime.android.datarepository.LimeBookingInformation
import com.lime.android.getLimeDataHolder
import com.lime.android.models.vehicles.MODVehiclesRequest
import com.lime.android.models.vehicles.MODVehiclesResponse
import com.lime.android.models.vehicles.Vehicle
import com.lime.android.networkhelper.ServiceResult
import com.lime.android.sharedrepository.LimeSharedRepositoryImpl
import com.lime.android.ui.navigationui.NavigationViewModel
import kotlinx.coroutines.launch

class TruckListViewModel(private val arguments: Bundle,private val context: Context): NavigationViewModel() {
    private val vehicleId = TruckListDestination.getVehicleId(arguments)
    val distance = TruckListDestination.getDistance(arguments)
    private val limeRepository: LimeRepository = LimeRepositoryImpl()
    var selectedVehicle: Vehicle? = null

    private val _vehicleList = MutableLiveData<List<Vehicle>?>()
    val vehicleList: LiveData<List<Vehicle>?> = _vehicleList

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean> = _spinner
    private val _serviceException = MutableLiveData<String?>()
    val serviceException: LiveData<String?> = _serviceException

    fun getVehicles(){
        viewModelScope.launch {
            showSpinner(true)
            when(val serviceResult =
                limeRepository.getVehicles(getVehiclesRequest())){
                is ServiceResult.Success -> onSuccessOfVehicles(serviceResult.data)
                is ServiceResult.Error -> onFailure(serviceResult.exception)
            }
        }
    }

    private fun onSuccessOfVehicles(response: MODVehiclesResponse?) {
        showSpinner(false)
        response?.let {
            if (response.success == 1){
                _vehicleList.value = response.vehicles
            }
        }
    }

    private fun onFailure(exception: String) {
        showSpinner(false)
        _serviceException.value = exception
    }

    private fun getVehiclesRequest(): MODVehiclesRequest {
        return MODVehiclesRequest(
            user_id = LimeSharedRepositoryImpl(context).loggedInUser.user_id,
            vehicle_id = vehicleId
        )
    }

    private fun showSpinner(show: Boolean) {
        _spinner.value = show
    }

    fun onClickProceed() {
        if (selectedVehicle != null){
            val dataHolder = getLimeDataHolder(arguments)
            if (dataHolder != null){
                val limeBookingInformation = LimeBookingInformation(
                    pickUpLat = dataHolder.pickUpLat,
                    pickUpLng = dataHolder.pickUpLng,
                    dropLat = dataHolder.dropLat,
                    dropLng = dataHolder.dropLng,
                    pickUpAddress = dataHolder.pickUpAddress,
                    dropAddress = dataHolder.dropAddress,
                    vehicle = selectedVehicle,
                    distance = dataHolder.distance
                )
                navigateTo(OrderDetailsDestination(DataHolder.build(limeBookingInformation)))
            }else{
                navigateTo(OrderDetailsDestination(DataHolder.build(LimeBookingInformation(vehicle = selectedVehicle))))
            }
        }else{
            _serviceException.value = context.getString(R.string.select_vehicle)
        }
    }
}
