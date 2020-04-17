package com.lime.android

import android.os.Bundle
import com.lime.android.datarepository.DataHolder
import com.lime.android.ui.navigationui.NavigationArguments
import com.lime.android.ui.navigationui.NavigationDestination
import com.lime.android.util.*

internal class TruckListDestination(vehicleId: Int, distance: Float, dataHolder: DataHolder):
    NavigationDestination(R.id.truckListFragment, getArgument(vehicleId,distance, dataHolder)){
    companion object{
        internal fun getVehicleId(arguments: Bundle) = arguments.getInt(VEHICLE_ID,0)
        internal fun getDistance(arguments: Bundle) = arguments.getFloat(DISTANCE,0.0f)
        internal fun getArgument(vehicleId: Int, distance: Float, dataHolder: DataHolder) = NavigationArguments.create{
            putInt(VEHICLE_ID,vehicleId)
            putFloat(DISTANCE, distance)
            putParcelable(LIME_DATA_HOLDER, dataHolder)
        }
    }
}

internal class AdditionalDetailsDestination(vehicleId: Int, distance: Float, dataHolder: DataHolder ):
    NavigationDestination(R.id.additionalDetailsFragment, getArgument(vehicleId, distance, dataHolder)){
    companion object {
        internal fun getArgument(vehicleId: Int, distance: Float, dataHolder: DataHolder) =
            NavigationArguments.create {
                putInt(VEHICLE_ID, vehicleId)
                putFloat(DISTANCE, distance)
                putParcelable(LIME_DATA_HOLDER, dataHolder)
            }
    }
}
internal class OfferBidDestination: NavigationDestination(R.id.offerBidFragment)
internal class BidConfirmationDestination: NavigationDestination(R.id.fragmentBidConfirmation)
internal class OrderDetailsDestination(dataHolder: DataHolder)
    : NavigationDestination(R.id.orderDetailsFragment, getArguments(dataHolder)){
    companion object{
        internal fun getArguments(dataHolder: DataHolder) = NavigationArguments.create{
                putParcelable(LIME_DATA_HOLDER, dataHolder)
        }
    }
}
internal class BillingDetailsDestination: NavigationDestination(R.id.billingDetailsFragment)
internal class OrderConfirmationDestination: NavigationDestination(R.id.orderConfirmationFragment)

internal fun getLimeDataHolder(arguments: Bundle) = arguments.getParcelable<DataHolder>(
    LIME_DATA_HOLDER)
