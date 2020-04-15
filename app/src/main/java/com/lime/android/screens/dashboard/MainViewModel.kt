package com.lime.android.screens.dashboard

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lime.android.apprepository.LimeRepository
import com.lime.android.apprepository.LimeRepositoryImpl
import com.lime.android.models.main.MODFcmUpdateRequest
import com.lime.android.models.main.MODFcmUpdateResponse
import com.lime.android.networkhelper.ServiceResult
import com.lime.android.sharedrepository.LimeSharedRepositoryImpl
import com.lime.android.util.GLOBAL_TAG
import com.lime.android.util.LimeUtils
import kotlinx.coroutines.launch

class MainViewModel(private val context: Context): ViewModel() {
    private val limeRepository: LimeRepository = LimeRepositoryImpl()

    fun updateFcmToken(fcmToken: String?) {
        fcmToken?.let {
            viewModelScope.launch {
                when(val serviceResult =
                    limeRepository.updateFcm(createFcmUpdateRequest())){
                    is ServiceResult.Success -> onSuccessUpdate(serviceResult.data)
                    is ServiceResult.Error -> onFailure(serviceResult.exception)
                }
            }
        }
    }

    private fun onFailure(exception: String) {

    }

    private fun onSuccessUpdate(response: MODFcmUpdateResponse?) {
        if (response?.success == 1)
            Log.d(GLOBAL_TAG,"FCM token Updated")
        else
            Log.d(GLOBAL_TAG,"FCM token Not updated")
    }

    private fun createFcmUpdateRequest(): MODFcmUpdateRequest {
        return MODFcmUpdateRequest(
            user_id = LimeSharedRepositoryImpl(context).loggedInUser.user_id,
            fcm_id = LimeSharedRepositoryImpl(context).fcmToken.orEmpty(),
            device_token = LimeUtils.getDeviceID(context).orEmpty()
        )
    }


}
