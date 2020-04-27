package com.lime.android.networkhelper

import android.util.Log
import com.google.gson.Gson
import com.lime.android.util.DEBUG_APP
import com.lime.android.util.LimeApp
import com.lime.android.util.LimeUtils
import retrofit2.Response

object RetrofitCallbackHandler {
    suspend fun <T> processCall(
        call: suspend () -> Response<T>
    ): ServiceResult<T> {
        return try {

            val serviceCallback = call()
            val body = serviceCallback.body()
            if (LimeApp.appSetup == DEBUG_APP){
                Log.d("Req_url",serviceCallback.raw().request.url.toUrl().toString())
                Log.d("Req_param",LimeUtils.stringifyRequestBody(serviceCallback.raw().request))
                Log.d("Req_Response", Gson().toJson(body))
            }
            if (serviceCallback.isSuccessful && body != null) {
                ServiceResult.Success(body)
            } else {
                ServiceResult.Error("Data services are not currently available. Please try again later.")
            }

        } catch (exception: Exception) {
            ServiceResult.Error(exception.localizedMessage.orEmpty())
        }
    }
}
