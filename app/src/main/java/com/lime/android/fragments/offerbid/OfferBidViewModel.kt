package com.lime.android.fragments.offerbid

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lime.android.*
import com.lime.android.OfferBidDestination
import com.lime.android.OrderDetailsDestination
import com.lime.android.apprepository.LimeRepository
import com.lime.android.apprepository.LimeRepositoryImpl
import com.lime.android.datarepository.DataHolder
import com.lime.android.datarepository.LimeBookingInformation
import com.lime.android.getLimeDataHolder
import com.lime.android.models.vehicles.Vehicle
import com.lime.android.ui.navigationui.NavigationViewModel
import com.lime.android.util.BOOKING_TYPE_BID
import com.lime.android.util.GLOBAL_TAG

class OfferBidViewModel(private val arguments: Bundle, private val context: Context): NavigationViewModel() {
    private val vehicleId = OfferBidDestination.getVehicleId(arguments)
    val distance = OfferBidDestination.getDistance(arguments)
    private val limeRepository: LimeRepository = LimeRepositoryImpl()
    private var offeredBidPrice: Double = 0.00

    private val _formattedBidPrice = MutableLiveData<String?>()
    val formattedBidPrice: LiveData<String?> = _formattedBidPrice
    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean> = _spinner
    private val _serviceException = MutableLiveData<String?>()
    val serviceException: LiveData<String?> = _serviceException

    fun onBidPriceChanged(changedBidPrice: String) {
        offeredBidPrice = getFormattedBidPrice(changedBidPrice)
        Log.d(GLOBAL_TAG,offeredBidPrice.toString())
    }

    private fun getFormattedBidPrice(changedBidPrice: String): Double {
        return if (TextUtils.isEmpty(changedBidPrice)){
             0.00
        }else{
            changedBidPrice.toDouble()
        }
    }


    fun onSendBidClicked() {
        if (offeredBidPrice != 0.0){
            val dataHolder = getLimeDataHolder(arguments)
            if (dataHolder != null){
                val limeBookingInformation = LimeBookingInformation(
                    pickUpLat = dataHolder.pickUpLat,
                    pickUpLng = dataHolder.pickUpLng,
                    dropLat = dataHolder.dropLat,
                    dropLng = dataHolder.dropLng,
                    pickUpAddress = dataHolder.pickUpAddress,
                    dropAddress = dataHolder.dropAddress,
                    distance = dataHolder.distance,
                    travelDate = dataHolder.travelDate,
                    bidAmount = offeredBidPrice,
                    vehicleTypeId = dataHolder.vehicleTypeId
                )
                navigateTo(BillingDetailsDestination(DataHolder.build(limeBookingInformation), BOOKING_TYPE_BID))
            }else{
                navigateTo(BillingDetailsDestination(DataHolder.build(LimeBookingInformation(bidAmount = offeredBidPrice)),BOOKING_TYPE_BID))
            }
        }else{
            _serviceException.value = context.getString(R.string.enter_bid_amount)
        }
        //navigateTo(BidConfirmationDestination())
    }
}
