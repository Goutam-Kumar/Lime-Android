package com.lime.android.fragments.home

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lime.android.R
import com.lime.android.util.LimeUtils
import kotlinx.android.synthetic.main.item_vehicle_category.view.*


internal class VehicleCategoryAdapter(private val adapterOnClick : ( position : Int) -> Unit, private val context: Context):
    RecyclerView.Adapter<VehicleCategoryAdapter.VehicleHolder>() {
    private val dummy_url: String = "https://www.tatamotors.com/wp-content/uploads/2018/01/19065024/prima.jpg"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleHolder {
        return VehicleHolder(LayoutInflater.from(context).inflate(R.layout.item_vehicle_category,parent,false))
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: VehicleHolder, position: Int) {
        holder.bind(position)
    }

    inner class VehicleHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        private val vehicleImage = itemView.vehicle_image
        private val vehicleName = itemView.tv_vehicle_name
        private val vehicle = itemView.lin_vehicle

        fun bind(position: Int){
            LimeUtils.setImageUsingPicasso(context,vehicleImage,dummy_url)
            vehicle.setOnTouchListener { _, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN ->{
                        vehicleName.setBackgroundResource(R.drawable.round_text_view_green_bg)
                        vehicleImage.borderColor = ContextCompat.getColor(context,R.color.colorPrimary)
                        adapterOnClick(position)
                        return@setOnTouchListener true
                    }
                    MotionEvent.ACTION_UP -> {
                        vehicleName.setBackgroundResource(R.drawable.round_text_view_black_bg)
                        vehicleImage.borderColor = ContextCompat.getColor(context,R.color.black)
                     return@setOnTouchListener true
                    }
                    else -> return@setOnTouchListener false
                }
            }
        }

    }
}
