package com.lime.android.models.main

data class MODFcmUpdateRequest (
    val user_id: Int = 0,
    val fcm_id: String="",
    val device_token: String= ""
)
