package com.lime.android.fragments.orderhistory.currentorder

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lime.android.apprepository.LimeRepository
import com.lime.android.apprepository.LimeRepositoryImpl
import com.lime.android.models.orderhistory.MODCurrentOrderRequest
import com.lime.android.models.orderhistory.MODCurrentOrderResponse
import com.lime.android.models.orderhistory.Ride
import com.lime.android.networkhelper.ServiceResult
import com.lime.android.sharedrepository.LimeSharedRepositoryImpl
import com.lime.android.ui.navigationui.NavigationViewModel
import kotlinx.coroutines.launch

class CurrentOrderViewModel(private val context: Context): NavigationViewModel() {
    private val limeRepository: LimeRepository = LimeRepositoryImpl()
    
    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean> = _spinner
    private val _serviceException = MutableLiveData<String?>()
    val serviceException: LiveData<String?> = _serviceException
    private val _listOfRides = MutableLiveData<List<Ride>?>()
    val listOfRides: LiveData<List<Ride>?> = _listOfRides
    val custId:Int = LimeSharedRepositoryImpl(context).loggedInUser.user_id
    val apiToken = LimeSharedRepositoryImpl(context).loggedInUser.api_token

    fun getCurrentOrders() {
        viewModelScope.launch {
            showSpinner(true)
            when(val serviceResult =
                limeRepository.getCurrentOrder(getCurrentOrderRequest(),"Bearer ".plus(apiToken))){
                is ServiceResult.Success -> onSuccessOfCurrentOrders(serviceResult.data)
                is ServiceResult.Error -> onFailure(serviceResult.exception)
            }
        }
    }

    private fun getCurrentOrderRequest(): MODCurrentOrderRequest {
        return MODCurrentOrderRequest(
            customer_id = custId
        )
    }

    private fun onFailure(exception: String) {
        showSpinner(false)
        _serviceException.value = exception
    }

    private fun onSuccessOfCurrentOrders(response: MODCurrentOrderResponse?) {
        showSpinner(false)
        response?.let {
            if (response.success == 1){
                _listOfRides.value = response.data?.rides
            }else{
                _listOfRides.value = emptyList()
            }
        }
    }

    private fun showSpinner(show: Boolean) {
        _spinner.value = show
    }

}
