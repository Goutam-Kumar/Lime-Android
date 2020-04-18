package com.lime.android.screens.register

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
import com.lime.android.models.register.MODRegisterRequest
import com.lime.android.models.register.MODRegisterResponse
import com.lime.android.networkhelper.ServiceResult
import com.lime.android.sharedrepository.LimeSharedRepositoryImpl
import com.lime.android.util.INDIVIDUAL
import com.lime.android.util.LimeUtils
import kotlinx.coroutines.launch

class RegisterViewModel(private val context: Context): ViewModel() {
    private val limeRepository: LimeRepository = LimeRepositoryImpl()

    var name: String = ""
    var phone: String = ""
    var email: String = ""
    var password: String = ""
    var confPassword: String = ""
    var custType: String = INDIVIDUAL
    var tnc: Boolean = false

    private var _nameError = MutableLiveData<String>()
    var nameError: LiveData<String> = _nameError
    private var _phoneError = MutableLiveData<String>()
    var phoneError: LiveData<String> = _phoneError
    private var _emailError = MutableLiveData<String>()
    var emailError: LiveData<String> = _emailError
    private var _passwordError = MutableLiveData<String>()
    var passwordError: LiveData<String> = _passwordError
    private var _confPasswordError = MutableLiveData<String>()
    var confPasswordError: LiveData<String> = _confPasswordError

    private val _serviceException = MutableLiveData<String?>()
    val serviceException: LiveData<String?> = _serviceException
    private var _progress = MutableLiveData<Boolean>()
    var progress: LiveData<Boolean> = _progress

    fun validateFields(){
        if (validateAll()){
            viewModelScope.launch {
                when(val serviceResult =
                    limeRepository.registerUser(getRegisterRequest())){
                    is ServiceResult.Success -> onSuccessRegister(serviceResult.data)
                    is ServiceResult.Error -> onFailure(serviceResult.exception)
                }
            }
        }
    }

    private fun onFailure(exception: String) {
        _serviceException.value = exception
    }

    private fun onSuccessRegister(response: MODRegisterResponse?) {
        response?.let {
            if (response.success == 0){
                onFailure(response.message)
            }
            _progress.value = response.success == 1
        }
    }

    private fun getRegisterRequest(): MODRegisterRequest {
        return MODRegisterRequest(
            emailid = email,
            mode = "android",
            mobno = phone,
            device_token = LimeUtils.getDeviceID(context).orEmpty(),
            fcm_id = LimeSharedRepositoryImpl(context).fcmToken.orEmpty(),
            gender = "M",
            password = password,
            phone_code = context.getString(R.string.malawi_std_code),
            user_name = name,
            customer_type = custType
        )
    }

    private fun validateAll(): Boolean{
        var isValid = true
        when {
            TextUtils.isEmpty(name) -> {
                isValid = false
                _nameError.value = context.getString(R.string.name_error)
            }
            TextUtils.isEmpty(phone) -> {
                isValid = false
                _phoneError.value = context.getString(R.string.mobile_error)
            }
            phone.length >= 10 -> {
                isValid = false
                _phoneError.value = context.getString(R.string.mobile_format_error)
            }
            TextUtils.isEmpty(email) -> {
                isValid = false
                _emailError.value = context.getString(R.string.email_empty_eror)
            }
            !LimeUtils.isEmailValid(email) -> {
                isValid = false
                _emailError.value = context.getString(R.string.email_format_error)
            }
            TextUtils.isEmpty(password) -> {
                isValid = false
                _passwordError.value = context.getString(R.string.password_error)
            }
            TextUtils.isEmpty(confPassword) -> {
                isValid = false
                _confPasswordError.value = context.getString(R.string.confirm_password_error)
            }
            (!confPassword.equals(password)) -> {
                isValid = false
                _confPasswordError.value = context.getString(R.string.confirm_password_same)
            }
            (confPassword.length < 6) ->{
                isValid = false
                _confPasswordError.value = context.getString(R.string.password_length)
            }
            !tnc -> {
                isValid = false
                _serviceException.value = context.getString(R.string.check_tnc)
            }
        }
        return isValid
    }
}
