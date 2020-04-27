package com.lime.android.datarepository

import android.os.Parcelable
import com.lime.android.models.vehicles.Vehicle
import kotlinx.android.parcel.Parcelize

@Parcelize
internal class DataHolder constructor(
    val pickUpLat: Double = 0.0,
    val pickUpLng: Double = 0.0,
    val dropLat: Double = 0.0,
    val dropLng: Double = 0.0,
    val pickUpAddress: String? = null,
    val dropAddress: String? = null,
    val vehicle: Vehicle? = null,
    val distance: Float = 0.0f,
    val travelDate: String? = null
):Parcelable {
    companion object {
        fun build(rigsInformationData: LimeBookingInformation?) = Builder().initData(rigsInformationData).build()
    }

    internal class Builder {
        internal fun initData(data : LimeBookingInformation?): Builder{
            buildFromRigsItInformationData(data)
            return this
        }

        private fun buildFromRigsItInformationData(data: LimeBookingInformation?) {
            data?.let {
                pickUpLat = it.pickUpLat
                pickUpLng = it.pickUpLng
                dropLat = it.dropLat
                dropLng = it.dropLng
                pickUpAddress = it.pickUpAddress
                dropAddress = it.dropAddress
                vehicle = it.vehicle
                distance = it.distance
                travelDate = it.travelDate
            }
        }

        private var pickUpLat: Double = 0.0
        private var pickUpLng: Double = 0.0
        private var dropLat: Double = 0.0
        private var dropLng: Double = 0.0
        private var pickUpAddress: String? = null
        private var dropAddress: String? = null
        private var vehicle: Vehicle? = null
        private var distance: Float = 0.0f
        private var travelDate: String? = null

        internal fun build() = DataHolder(
            pickUpLat,
            pickUpLng,
            dropLat,
            dropLng,
            pickUpAddress,
            dropAddress,
            vehicle,
            distance,
            travelDate
        )
    }
}
