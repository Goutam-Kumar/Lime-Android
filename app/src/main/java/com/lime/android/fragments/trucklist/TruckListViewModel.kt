package com.lime.android.fragments.trucklist

import android.content.Context
import android.os.Bundle
import com.lime.android.OrderDetailsDestination
import com.lime.android.ui.navigationui.NavigationViewModel

class TruckListViewModel: NavigationViewModel() {

    fun onClickProceed() {
        navigateTo(OrderDetailsDestination())
    }
}
