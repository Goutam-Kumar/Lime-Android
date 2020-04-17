package com.lime.android.models.vehicles

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vehicle(
    val color: String?,
    val created_at: String?,
    val deleted_at: String?,
    val dimensions: String?,
    val engine_type: String?,
    val group_id: Int = 0,
    val horse_power: String?,
    val id: Int = 0,
    val in_service: Int = 0,
    val int_mileage: Int = 0,
    val lic_exp_date: String?,
    val license_plate: String?,
    val make: String?,
    val meta_data: MetaData?,
    val mileage: String?,
    val model: String?,
    val per_km_price: String?,
    val reg_exp_date: String?,
    val type: String?,
    val type_id: Int = 0,
    val tyres: String? = null,
    val updated_at: String? = null,
    val user_id: Int = 0,
    val vehicle_image: String? = null,
    val vehicle_load: String? = null,
    val vin: String? = null,
    val year: String? = null
): Parcelable
