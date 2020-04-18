package com.lime.android.fragments.orderconfirmation

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lime.android.datarepository.DataHolder
import com.lime.android.getLimeDataHolder
import com.lime.android.ui.navigationui.NavigationViewModel

internal class OrderConfirmationViewModel(private val context: Context, private val arguments: Bundle): NavigationViewModel() {
    private val dataholder: DataHolder? = getLimeDataHolder(arguments)

    private val _data = MutableLiveData<DataHolder?>()
    val data: LiveData<DataHolder?> = _data

    init {
        _data.value = dataholder
    }
}
