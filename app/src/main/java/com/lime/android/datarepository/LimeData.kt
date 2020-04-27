package com.lime.android.datarepository

import com.lime.android.models.vehicles.Vehicle

sealed class LimeData

data class LimeBookingInformation(
    val pickUpLat: Double = 0.0,
    val pickUpLng: Double = 0.0,
    val dropLat: Double = 0.0,
    val dropLng: Double = 0.0,
    val pickUpAddress: String? = null,
    val dropAddress: String? = null,
    val vehicle: Vehicle? = null,
    val distance: Float = 0.0f,
    val travelDate: String? = null
): LimeData()
