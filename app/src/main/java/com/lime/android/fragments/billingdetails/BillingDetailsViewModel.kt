package com.lime.android.fragments.billingdetails

import com.lime.android.OrderConfirmationDestination
import com.lime.android.ui.navigationui.NavigationViewModel

class BillingDetailsViewModel: NavigationViewModel() {
    fun onBookNowClicked() {
        navigateTo(OrderConfirmationDestination())
    }
}
