package com.lime.android.fragments.home

import com.lime.android.TruckListDestination
import com.lime.android.ui.navigationui.NavigationViewModel

class HomeViewModel: NavigationViewModel() {

    fun onVehicleClicked(position: Int) {
        navigateTo(TruckListDestination())
    }
}
