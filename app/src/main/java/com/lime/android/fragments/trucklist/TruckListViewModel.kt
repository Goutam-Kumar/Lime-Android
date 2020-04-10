package com.lime.android.fragments.trucklist

import com.lime.android.OrderDetailsDestination
import com.lime.android.ui.navigationui.NavigationViewModel

class TruckListViewModel: NavigationViewModel() {
    fun onClickProceed() {
        navigateTo(OrderDetailsDestination())
    }
}
