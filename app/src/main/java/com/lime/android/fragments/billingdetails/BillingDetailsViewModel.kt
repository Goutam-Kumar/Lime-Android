package com.lime.android.fragments.billingdetails

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lime.android.OrderConfirmationDestination
import com.lime.android.R
import com.lime.android.apprepository.LimeRepository
import com.lime.android.apprepository.LimeRepositoryImpl
import com.lime.android.getLimeDataHolder
import com.lime.android.models.booking.MODBookingResponse
import com.lime.android.networkhelper.ServiceResult
import com.lime.android.sharedrepository.LimeSharedRepositoryImpl
import com.lime.android.ui.navigationui.NavigationViewModel
import com.lime.android.util.FileUtils
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import kotlin.math.roundToInt

internal class BillingDetailsViewModel(private val context: Context, private val arguments: Bundle): NavigationViewModel() {
    private val limeRepository: LimeRepository = LimeRepositoryImpl()
    private val dataHolder = getLimeDataHolder(arguments)
     var bookingType: String? = null
     var businessName: String? = null
     var businessEmail: String? = null
     var contactName: String? = null
     var address: String? = null
     var natId: String? = null
    var certificate: File? = null
    var bill: File? = null
    var certificateUri: Uri? = null
    var billUri: Uri? = null

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean> = _spinner
    private val _serviceException = MutableLiveData<String?>()
    var serviceException: LiveData<String?> = _serviceException

    fun onBookNowClicked() {
        if(validateAll()){
            viewModelScope.launch {
                showSpinner(true)
                when(val serviceResult =
                    limeRepository.bookNow(
                        auth = "Bearer "+ LimeSharedRepositoryImpl(context).loggedInUser.api_token,
                        dropAddress = getPartRequestBody(dataHolder?.dropAddress.orEmpty()),
                        dropLng = getPartRequestBody(dataHolder?.dropLat.toString()),
                        dropLat = getPartRequestBody(dataHolder?.dropLng.toString()),
                        pickup_address = getPartRequestBody(dataHolder?.pickUpAddress.toString()),
                        pickupLat = getPartRequestBody(dataHolder?.pickUpLat.toString()),
                        pickupLng = getPartRequestBody(dataHolder?.pickUpLng.toString()),
                        address = getPartRequestBody(address.orEmpty()),
                        bookingType = getPartRequestBody(bookingType.orEmpty()),
                        cName = getPartRequestBody(contactName.orEmpty()),
                        email = getPartRequestBody(businessEmail.orEmpty()),
                        fcmID = getPartRequestBody(LimeSharedRepositoryImpl(context).loggedInUser.fcm_id),
                        natID = getPartRequestBody(natId.orEmpty()),
                        noPerson = getPartRequestBody("0"),
                        userId = getPartRequestBody(LimeSharedRepositoryImpl(context).loggedInUser.user_id.toString()),
                        vehicleTypeId = getPartRequestBody(dataHolder?.vehicle?.type_id.toString()),
                        pickUpDate = getPartRequestBody(dataHolder?.travelDate.orEmpty()),
                        totalAmount = getPartRequestBody(((dataHolder?.vehicle?.per_km_price!!.toFloat() * dataHolder.distance).roundToInt()).toString()),
                        bill = getFilePart(bill,billUri,"consigner_bill"),
                        certificate = getFilePart(certificate,certificateUri,"coi")
                    )){
                    is ServiceResult.Success -> onSuccessOfVehicleTypes(serviceResult.data)
                    is ServiceResult.Error -> onFailure(serviceResult.exception)
                }
            }
        }
        //navigateTo(OrderConfirmationDestination())
    }

    private fun onSuccessOfVehicleTypes(response: MODBookingResponse?) {
        _spinner.value = false
        if (response?.success == 1){
            response.data?.booking_id
            navigateTo(OrderConfirmationDestination(dataHolder!!, response.data?.booking_id ?:0))
        }
    }

    private fun getPartRequestBody(input: String): RequestBody {
        return input.toRequestBody(okhttp3.MultipartBody.FORM)
    }

    private fun getFilePart(file: File?, fileUri: Uri?, paramName: String): MultipartBody.Part?{
        file?.let {
            val newFile = FileUtils.getFileFromUri(context,fileUri)
            val requestBody = newFile.asRequestBody(context.contentResolver.getType(fileUri!!).orEmpty().toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(paramName, file.name, requestBody);
        }
        return null
    }

    private fun onFailure(exception: String) {
        _spinner.value = false
        _serviceException.value = exception
    }

    private fun validateAll(): Boolean {
        var isValid = true
        if (TextUtils.isEmpty(bookingType)){
            isValid = false
            _serviceException.value = context.getString(R.string.select_bookingType)
        }else if (TextUtils.isEmpty(businessName)){
            isValid = false
            _serviceException.value = context.getString(R.string.enter_b_name)
        }else if (TextUtils.isEmpty(businessEmail)){
            isValid = false
            _serviceException.value = context.getString(R.string.enter_b_email)
        }else if (TextUtils.isEmpty(contactName)){
            isValid = false
            _serviceException.value = context.getString(R.string.enter_c_name)
        }else if (TextUtils.isEmpty(address)){
            isValid = false
            _serviceException.value = context.getString(R.string.enter_address)
        }else if (TextUtils.isEmpty(natId)) {
            isValid = false
            _serviceException.value = context.getString(R.string.enter_natid)
        }
        return isValid
    }

    private fun showSpinner(show: Boolean) {
        _spinner.value = show
    }
}
