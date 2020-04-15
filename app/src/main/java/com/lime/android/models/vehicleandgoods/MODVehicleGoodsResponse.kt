package com.lime.android.models.vehicleandgoods

data class MODVehicleGoodsResponse(
    val goods_type: List<GoodsType>,
    val success: Int = 0,
    val vehicles: List<Vehicle>,
    val message: String = ""
)
