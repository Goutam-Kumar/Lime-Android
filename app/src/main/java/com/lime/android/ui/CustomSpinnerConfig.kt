package com.lime.android.ui

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import com.lime.android.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomSpinnerConfig(
    val isCancellable: Boolean = false,
    val isTransparent: Boolean = true,
    val spinnerTag: String = "",
    val cancelContentDescription: String = "",
    @StringRes val hintMessageId: Int = R.string.please_wait
) : Parcelable {

    fun getWindowStyle() = if (isTransparent) {
        R.style.spinner_translucent_dim
    } else {
        R.style.spinner_translucent
    }

    fun getCancelContentDescription(context: Context): String =
        if (cancelContentDescription.isBlank()) {
            context.getString(R.string.cancel_service_call)
        } else {
            cancelContentDescription
        }
}

