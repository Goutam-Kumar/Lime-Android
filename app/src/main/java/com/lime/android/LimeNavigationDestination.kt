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
internal class OfferBidDestination(vehicleId: Int, distance: Float, dataHolder: DataHolder):
    NavigationDestination(R.id.offerBidFragment, getArgument(vehicleId,distance, dataHolder)){
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
internal class BidConfirmationDestination: NavigationDestination(R.id.action_billingDetailsFragment_to_fragmentBidConfirmation)
internal class OrderDetailsDestination(dataHolder: DataHolder)
    : NavigationDestination(R.id.orderDetailsFragment, getArguments(dataHolder)){
    companion object{
        internal fun getArguments(dataHolder: DataHolder) = NavigationArguments.create{
            putParcelable(LIME_DATA_HOLDER, dataHolder)

        }
    }
}
internal class BillingDetailsDestination(dataHolder: DataHolder, bookingType: String)
    : NavigationDestination(R.id.billingDetailsFragment, getArguments(dataHolder,bookingType)){
    companion object{
        internal fun getBookingType(arguments: Bundle) = arguments.getString(BOOKING_TYPE).orEmpty()
        internal fun getArguments(dataHolder: DataHolder, bookingType: String) = NavigationArguments.create{
            putParcelable(LIME_DATA_HOLDER, dataHolder)
            putString(BOOKING_TYPE, bookingType)
        }
    }
}
internal class OrderConfirmationDestination(dataHolder: DataHolder, bookingId: Int)
    : NavigationDestination(R.id.action_billingDetailsFragment_to_orderConfirmationFragment, getArguments(dataHolder,bookingId)){
    companion object{
        internal fun getBookingID(arguments: Bundle) = arguments.getInt(BOOKING_ID)
        internal fun getArguments(dataHolder: DataHolder, bookingId: Int) = NavigationArguments.create{
            putParcelable(LIME_DATA_HOLDER, dataHolder)
            putInt(BOOKING_ID, bookingId)
        }
    }
}

internal class OrderHistoryDestination: NavigationDestination(R.id.orderHistoryFragment)

internal fun getLimeDataHolder(arguments: Bundle) = arguments.getParcelable<DataHolder>(
    LIME_DATA_HOLDER)
