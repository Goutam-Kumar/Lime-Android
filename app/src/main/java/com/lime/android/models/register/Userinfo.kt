package com.lime.android.models.register

data class Userinfo(val gender: String = "",
                    val user_name: String = "",
                    val api_token: String = "",
                    val socialmedia_uid: String = "",
                    val profile_pic: String = "",
                    val emailid: String = "",
                    val mobno: String = "",
                    val password: String = "",
                    val user_id: Int = 0,
                    val device_token: String = "",
                    val fcm_id: String = "",
                    val phone_code: String = "",
                    val status: Int = 0,
                    val timestamp: String = "")
