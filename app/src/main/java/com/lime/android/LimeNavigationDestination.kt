package com.lime.android

import com.lime.android.ui.navigationui.NavigationArguments
import com.lime.android.ui.navigationui.NavigationDestination
import com.lime.android.util.PICKUP_LAT
import com.lime.android.util.PICKUP_LNG
import com.lime.android.util.VEHICLE_ID

internal class TruckListDestination: NavigationDestination(R.id.truckListFragment)

internal class AdditionalDetailsDestination(vehicleId: Int, pickupLat: Double, pickupLng: Double ):
    NavigationDestination(R.id.additionalDetailsFragment, getArgument(vehicleId, pickupLat, pickupLng)){
    companion object {
        internal fun getArgument(vehicleId: Int, pickupLat: Double, pickupLng: Double) =
            NavigationArguments.create {
                putInt(VEHICLE_ID, vehicleId)
                putString(PICKUP_LAT, pickupLat.toString())
                putString(PICKUP_LNG, pickupLng.toString())
            }
    }
}
internal class OfferBidDestination: NavigationDestination(R.id.offerBidFragment)
internal class BidConfirmationDestination: NavigationDestination(R.id.fragmentBidConfirmation)
internal class OrderDetailsDestination: NavigationDestination(R.id.orderDetailsFragment)
internal class BillingDetailsDestination: NavigationDestination(R.id.billingDetailsFragment)
internal class OrderConfirmationDestination: NavigationDestination(R.id.orderConfirmationFragment)
