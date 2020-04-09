package com.lime.android.fragments.offerbid

import com.lime.android.BidConfirmationDestination
import com.lime.android.ui.navigationui.NavigationViewModel

class OfferBidViewModel: NavigationViewModel() {
    fun onSendBidClicked() {
        navigateTo(BidConfirmationDestination())
    }
}
