package com.lime.android.models.register

data class MODRegisterRequest(val mode: String = "",
                              val mobno: String = "",
                              val password: String = "",
                              val gender: String = "",
                              val userName: String = "",
                              val deviceToken: String = "",
                              val emailid: String = "",
                              val fcmId: String = "",
                              val phoneCode: String = "")
