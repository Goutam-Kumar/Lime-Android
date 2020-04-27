package com.lime.android.fragments.orderhistory.completedorder

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lime.android.models.orderhistory.Ride
import com.lime.android.ui.navigationui.NavigationViewModel

class CompletedOrderViewHolder(private val context: Context): NavigationViewModel() {
    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean> = _spinner
    private val _serviceException = MutableLiveData<String?>()
    val serviceException: LiveData<String?> = _serviceException
    private val _listOfRides = MutableLiveData<List<Ride>?>()
    val listOfRides: LiveData<List<Ride>?> = _listOfRides

    init {
        _listOfRides.value = emptyList()
    }
}
