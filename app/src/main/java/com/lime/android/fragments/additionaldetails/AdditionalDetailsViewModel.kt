package com.lime.android.fragments.additionaldetails

import com.lime.android.OfferBidDestination
import com.lime.android.ui.navigationui.NavigationViewModel

class AdditionalDetailsViewModel: NavigationViewModel() {

    fun formattedDate(number: Int): String{
        if (number < 10){
            return "0".plus(number)
        }else if (number.toString().length == 4){
            return number.toString().takeLast(2)
        }else
            return number.toString()
    }

    fun onBidNowClicked() {
        navigateTo(OfferBidDestination())
    }
}
