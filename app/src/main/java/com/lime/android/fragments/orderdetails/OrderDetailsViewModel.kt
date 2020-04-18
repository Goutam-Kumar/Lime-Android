package com.lime.android.fragments.orderdetails

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lime.android.BillingDetailsDestination
import com.lime.android.datarepository.DataHolder
import com.lime.android.getLimeDataHolder
import com.lime.android.ui.navigationui.NavigationViewModel

internal class OrderDetailsViewModel(private val arguments: Bundle, private val context: Context): NavigationViewModel() {
    private val dataHolder: DataHolder? = getLimeDataHolder(arguments)

    private val _dataHolder = MutableLiveData<DataHolder?>()
    var limeDataHolder: LiveData<DataHolder?> = _dataHolder

    init {
        _dataHolder.value = dataHolder
    }

    fun onContinueClick() {
        navigateTo(BillingDetailsDestination(dataHolder!!))
    }
}
