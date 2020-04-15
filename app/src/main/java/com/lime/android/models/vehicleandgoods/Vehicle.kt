package com.lime.android.models.vehicleandgoods

data class Vehicle(
    val color: String,
    val created_at: String,
    val deleted_at: Any,
    val engine_type: String,
    val group_id: Int,
    val horse_power: String,
    val id: Int,
    val in_service: Int,
    val int_mileage: Int,
    val lic_exp_date: String,
    val license_plate: String,
    val make: String,
    val meta_data: MetaData,
    val mileage: Int,
    val model: String,
    val reg_exp_date: String,
    val type: Any,
    val type_id: Int,
    val updated_at: String,
    val user_id: Int,
    val vehicle_image: String,
    val vin: String,
    val year: String
)
