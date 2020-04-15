package com.lime.android.fragments.home

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lime.android.R
import com.lime.android.models.vehicleTypes.VehicleType
import com.lime.android.service.LIME_IMAGE_URL
import com.lime.android.util.LimeUtils
import kotlinx.android.synthetic.main.item_vehicle_category.view.*


internal class VehicleCategoryAdapter(
    private val adapterOnClick : ( position : Int, vehicleId: Int) -> Unit,
    private val context: Context,
    private val vehicles: List<VehicleType>?):
    RecyclerView.Adapter<VehicleCategoryAdapter.VehicleHolder>() {
    private var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleHolder {
        return VehicleHolder(LayoutInflater.from(context).inflate(R.layout.item_vehicle_category,parent,false))
    }

    override fun getItemCount(): Int {
        return vehicles?.size ?: 0
    }

    override fun onBindViewHolder(holder: VehicleHolder, position: Int) {
        if(itemCount > 0)
            vehicles?.let {
                holder.bind(position, vehicles.get(position))
            }
    }

    inner class VehicleHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        private val vehicleImage = itemView.vehicle_image
        private val vehicleName = itemView.tv_vehicle_name
        private val vehicle = itemView.lin_vehicle

        fun bind(position: Int,vcl: VehicleType){
            LimeUtils.setImageUsingPicasso(context,vehicleImage,LIME_IMAGE_URL.plus(vcl.icon))
            vehicleName.text = vcl.vehicletype

            if (selectedPosition == adapterPosition) {
                vehicleName.setBackgroundResource(R.drawable.round_text_view_green_bg)
                vehicleImage.borderColor = ContextCompat.getColor(context,R.color.colorPrimary)
            } else {
                vehicleName.setBackgroundResource(R.drawable.round_text_view_black_bg)
                vehicleImage.borderColor = ContextCompat.getColor(context,R.color.black)
            }

            vehicle.setOnClickListener {
                vehicleName.setBackgroundResource(R.drawable.round_text_view_green_bg)
                vehicleImage.borderColor = ContextCompat.getColor(context,R.color.colorPrimary)
                notifyItemChanged(adapterPosition)
                if (selectedPosition != adapterPosition) {
                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                }
                adapterOnClick(position, vcl.id)
            }
        }
    }
}
