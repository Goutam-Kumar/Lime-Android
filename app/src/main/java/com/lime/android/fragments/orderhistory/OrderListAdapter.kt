package com.lime.android.fragments.orderhistory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lime.android.R
import com.lime.android.models.orderhistory.Ride
import com.lime.android.util.LimeUtils

class OrderListAdapter(
    private val onAdapterClicked:(vehicle: Ride) -> Unit,
    private val context: Context,
    private val listOfOrders: List<Ride>
): RecyclerView.Adapter<OrderListAdapter.OrderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        return OrderHolder(LayoutInflater.from(context).inflate(R.layout.item_order_history,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfOrders.size
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.bind(position,listOfOrders[position])
    }

    inner class OrderHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, ride: Ride){
            itemView.apply {
                try {
                    setOnClickListener { onAdapterClicked(ride) }
                    findViewById<TextView>(R.id.tv_order_id).text = "#".plus(ride.booking_id)
                    findViewById<TextView>(R.id.tv_time).text = LimeUtils.getFormattedDate(ride.book_date.orEmpty(),"yyyy-MM-dd","dd-MM-yyyy")
                    findViewById<TextView>(R.id.pick_up_loc).text = ride.source_address
                    findViewById<TextView>(R.id.drop_loc).text = ride.dest_address
                    findViewById<TextView>(R.id.tv_price).text = context.getString(R.string.price).plus("  ").plus(context.getString(R.string.mw)).plus("  ").plus(
                        ride.amount)
                    findViewById<TextView>(R.id.tv_fare).text = context.getString(R.string.received).plus("  ").plus(context.getString(R.string.mw)).plus("  ").plus(
                        ride.amount)
                    findViewById<TextView>(R.id.tv_vehicle).text = context.getString(R.string.truck).plus("  ").plus(ride.vechile_details?.model)
                    findViewById<TextView>(R.id.tv_reached_time).text = LimeUtils.getFormattedDate(ride.dest_time.orEmpty(),"yyyy-MM-dd hh:mm:ss", "dd-MMM")
                        .plus("  ").plus(LimeUtils.getFormattedDate(ride.dest_time.orEmpty(),"yyyy-MM-dd hh:mm:ss", "hh-mm aa"))
                    findViewById<TextView>(R.id.tv_status).text = ride.ride_status
                }catch (e: Exception){
                    e.printStackTrace()
                }

            }
        }
    }
}
