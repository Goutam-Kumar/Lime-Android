package com.lime.android.networkhelper

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClient {
    private var logging = false

    private val instanceDelegate = lazy {
        OkHttpClient.Builder().apply {
            if (logging) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }.callTimeout(3, TimeUnit.MINUTES)
            .build()
    }
    val instance by instanceDelegate
    fun config(enableLogging: Boolean = false) {
        if (instanceDelegate.isInitialized()) {
            Log.e("Error","Can't reconfigure OkHttpClient after initialization")
            return
        }

        logging = enableLogging
    }

}
