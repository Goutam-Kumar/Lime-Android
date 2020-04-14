package com.lime.android.screens.login

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lime.android.R
import com.lime.android.apprepository.LimeRepository
import com.lime.android.apprepository.LimeRepositoryImpl
import com.lime.android.models.login.MODLoginRequest
import com.lime.android.models.login.MODLoginResponse
import com.lime.android.networkhelper.ServiceResult
import com.lime.android.sharedrepository.LimeSharedRepositoryImpl
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context): ViewModel() {
    private val limeRepository: LimeRepository = LimeRepositoryImpl()

    private var mobNum: String = ""

    private var _mobileError = MutableLiveData<String>()
    var mobileError: LiveData<String> = _mobileError
    private var _progress = MutableLiveData<Boolean>()
    var progress: LiveData<Boolean> = _progress
    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean> = _spinner
    private val _serviceException = MutableLiveData<String?>()
    val serviceException: LiveData<String?> = _serviceException

    fun validateFields() {
        //_progress.value = validateAllFields()
        if (validateAllFields()){
            viewModelScope.launch {
                showSpinner(true)
                when(val serviceResult =
                    limeRepository.loginUser(getLoginRequest())){
                    is ServiceResult.Success -> onSuccessLogin(serviceResult.data)
                    is ServiceResult.Error -> onFailure(serviceResult.exception)
                }
            }
        }
    }

    private fun onFailure(exception: String) {
        showSpinner(false)
    }

    private fun onSuccessLogin(response: MODLoginResponse?) {
        showSpinner(false)
        response?.let {
            LimeSharedRepositoryImpl(context).loggedInUser = response.data.userinfo
            _progress.value = response.success == 1
        }
    }

    private fun getLoginRequest(): MODLoginRequest {
        return MODLoginRequest(
            mobno = mobNum
        )
    }

    private fun validateAllFields(): Boolean {
        var isValid = true
        if (TextUtils.isEmpty(mobNum)){
            isValid = false
            _mobileError.value = context.getString(R.string.mobile_error)
        }else if (mobNum.length != 10){
            isValid = false
            _mobileError.value = context.getString(R.string.mobile_format_error)
        }
        return isValid
    }

    fun onMobileNumberChanged(mobileNumber: String) {
        mobNum = mobileNumber
    }

    private fun showSpinner(show: Boolean) {
        _spinner.value = show
    }

}
