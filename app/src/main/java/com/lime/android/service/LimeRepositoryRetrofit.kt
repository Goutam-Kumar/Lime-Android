package com.lime.android.service

import com.lime.android.networkhelper.OkHttpClient
import com.lime.android.networkhelper.RetrofitServiceFactory
import java.util.concurrent.TimeUnit
const val LIME_IMAGE_URL: String = "http://103.143.46.61/cargo/uploads/"
internal class LimeRepositoryRetrofit {
    private val LIME_BASE_URL: String = "http://103.143.46.61/cargo/api/"

    fun prepareService(): LimeRepositoryServices {
        val retrofitService: LimeRepositoryServices by lazy {
            RetrofitServiceFactory.getProvider(
                baseUrl = LIME_BASE_URL,
                serviceClass = LimeRepositoryServices::class.java,
                client = OkHttpClient.instance.newBuilder()
                    .connectTimeout(180 , TimeUnit.SECONDS)
                    .readTimeout(180 , TimeUnit.SECONDS)
                    .build()
            )
        }
        return retrofitService
    }
}
