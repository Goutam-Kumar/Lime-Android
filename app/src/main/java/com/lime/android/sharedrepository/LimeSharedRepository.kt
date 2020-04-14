package com.lime.android.sharedrepository

import android.content.Context
import com.lime.android.models.login.Userinfo

interface LimeSharedRepository {
    var loggedInUser: Userinfo

    fun storeData(data: String): String
    fun fetchData(key: String): String
    fun cleanup()

    companion object {
        fun cleanLime(context: Context) {
            LimeSharedRepositoryImpl(context).cleanup()
        }
    }
}
