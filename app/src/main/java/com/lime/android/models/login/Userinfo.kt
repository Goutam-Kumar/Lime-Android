package com.lime.android.models.login

data class Userinfo(val gender: String = "",
                    val userName: String = "",
                    val apiToken: String = "",
                    val socialmediaUid: String = "",
                    val profilePic: String = "",
                    val emailid: String = "",
                    val mobno: String = "",
                    val userType: String = "",
                    val userId: Int = 0,
                    val deviceToken: String = "",
                    val fcmId: String = "",
                    val phoneCode: String = "",
                    val status: Int = 0,
                    val timestamp: String = "")
