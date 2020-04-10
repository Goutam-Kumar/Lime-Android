package com.lime.android.fragments.orderdetails

import com.lime.android.BillingDetailsDestination
import com.lime.android.ui.navigationui.NavigationViewModel

class OrderDetailsViewModel: NavigationViewModel() {
    fun onContinueClick() {
        navigateTo(BillingDetailsDestination())
    }
}
