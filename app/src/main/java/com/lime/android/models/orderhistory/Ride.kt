package com.lime.android.models.orderhistory

data class Ride(
    val amount: String? = null,
    val book_date: String? = null,
    val book_time: String? = null,
    val booking_id: Int = 0,
    val dest_address: String? = null,
    val dest_time: String? = null,
    val driving_time: String? = null,
    val ride_status: String? = null,
    val source_address: String? = null,
    val source_time: String? = null,
    val total_kms: String? = null,
    val user_id: Int = 0,
    val vechile_details: VechileDetails?
)
