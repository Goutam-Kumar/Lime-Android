package com.lime.android.models.vehicles

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MetaData(
    val average: String?,
    val documents: String?,
    val driver_id: Int = 0,
    val ins_exp_date: String?,
    val ins_number: String?,
    val price_per_km: String?,
    val purchase_info: String?,
    val udf: String?
):Parcelable
