package com.lime.android

import android.os.Bundle
import com.lime.android.ui.navigationui.NavigationArguments
import com.lime.android.ui.navigationui.NavigationDestination
import com.lime.android.util.DISTANCE
import com.lime.android.util.PICKUP_LAT
import com.lime.android.util.PICKUP_LNG
import com.lime.android.util.VEHICLE_ID

internal class TruckListDestination(vehicleId: Int, distance: Float): NavigationDestination(R.id.truckListFragment, getArgument(vehicleId,distance)){
    companion object{
        internal fun getVehicleId(arguments: Bundle) = arguments.getInt(VEHICLE_ID,0)
        internal fun getDistance(arguments: Bundle) = arguments.getFloat(DISTANCE,0.0f)
        internal fun getArgument(vehicleId: Int, distance: Float) = NavigationArguments.create{
            putInt(VEHICLE_ID,vehicleId)
            putFloat(DISTANCE, distance)
        }
    }
}

internal class AdditionalDetailsDestination(vehicleId: Int, distance: Float ):
    NavigationDestination(R.id.additionalDetailsFragment, getArgument(vehicleId, distance)){
    companion object {
        internal fun getArgument(vehicleId: Int, distance: Float) =
            NavigationArguments.create {
                putInt(VEHICLE_ID, vehicleId)
                putFloat(DISTANCE, distance)
            }
    }
}
internal class OfferBidDestination: NavigationDestination(R.id.offerBidFragment)
internal class BidConfirmationDestination: NavigationDestination(R.id.fragmentBidConfirmation)
internal class OrderDetailsDestination: NavigationDestination(R.id.orderDetailsFragment)
internal class BillingDetailsDestination: NavigationDestination(R.id.billingDetailsFragment)
internal class OrderConfirmationDestination: NavigationDestination(R.id.orderConfirmationFragment)
