package com.lime.android.models.register

data class MODRegisterRequest(val mode: String = "",
                              val mobno: String = "",
                              val password: String = "",
                              val gender: String = "",
                              val user_name: String = "",
                              val device_token: String = "",
                              val emailid: String = "",
                              val fcm_id: String = "",
                              val phone_code: String = "",
                              val customer_type: String = "")
