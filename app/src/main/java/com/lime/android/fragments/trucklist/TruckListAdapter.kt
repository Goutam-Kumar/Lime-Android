package com.lime.android.fragments.trucklist

import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lime.android.R
import com.lime.android.models.vehicles.Vehicle
import com.lime.android.service.LIME_IMAGE_URL
import com.lime.android.util.LimeUtils
import kotlinx.android.synthetic.main.item_truck_list.view.*
import kotlin.math.roundToInt

internal class TruckListAdapter(
    private val onAdapterClicked:(vehicle: Vehicle) -> Unit,
    private val context: Context,
    private val listOfVehicles: List<Vehicle>,
    private val distance: Float = 0.0f
) : RecyclerView.Adapter<TruckListAdapter.TruckHolder>() {
    private var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TruckHolder {
        return TruckHolder(LayoutInflater.from(context).inflate(R.layout.item_truck_list,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfVehicles.size
    }

    override fun onBindViewHolder(holder: TruckHolder, position: Int) {
        holder.bind(position,listOfVehicles[position])
    }

    inner class TruckHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imgUserDp = itemView.img_user_dp
        private val tvPrice = itemView.tv_price
        private val parent = itemView.lin_parent_layout

        fun bind(position: Int, vehicle: Vehicle){
            LimeUtils.setImageUsingPicasso(context,imgUserDp, LIME_IMAGE_URL.plus(vehicle.vehicle_image))
            vehicle.per_km_price?.let {
                tvPrice.text = context.getString(R.string.currency).plus(" ").plus((vehicle.per_km_price.toFloat() * distance).roundToInt() )
            }
            itemView.apply {
                findViewById<TextView>(R.id.vehicle_name).text = vehicle.model
                findViewById<TextView>(R.id.tv_truck).text = vehicle.make
                findViewById<TextView>(R.id.tv_weight).text = vehicle.vehicle_load
                findViewById<TextView>(R.id.tv_tyre).text = vehicle.tyres.plus(" Tyres")
            }

            if (selectedPosition == adapterPosition) {
                changePriceBg(true)
            } else {
                changePriceBg(false)
            }
            parent.setOnClickListener {
                onAdapterClicked(vehicle)
                changePriceBg(true)
                notifyItemChanged(adapterPosition)
                if (selectedPosition != adapterPosition) {
                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                }
            }
        }

        private fun changePriceBg(isSelected: Boolean) {
            itemView.apply {
                if(isSelected){
                    findViewById<LinearLayout>(R.id.lin_left).setBackgroundResource(R.drawable.trans_text_bg)
                    findViewById<TextView>(R.id.tv_price).apply {
                        setBackgroundResource(R.drawable.trans_text_bg)
                        setTextColor(ContextCompat.getColor(context,R.color.black))
                    }
                    findViewById<LinearLayout>(R.id.lin_parent_layout).setBackgroundResource(R.drawable.border_bg)
                }else{
                    findViewById<LinearLayout>(R.id.lin_left).setBackgroundResource(R.drawable.edit_text_bg)
                    findViewById<TextView>(R.id.tv_price).apply {
                        setBackgroundResource(R.drawable.primary_button_selector)
                        setTextColor(ContextCompat.getColor(context,R.color.white))
                    }
                    findViewById<LinearLayout>(R.id.lin_parent_layout).setBackgroundResource(R.drawable.trans_text_bg)
                }
            }
        }
    }
}
