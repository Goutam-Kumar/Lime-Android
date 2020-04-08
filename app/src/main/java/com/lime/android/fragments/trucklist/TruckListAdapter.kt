package com.lime.android.fragments.trucklist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lime.android.R
import com.lime.android.util.LimeUtils
import kotlinx.android.synthetic.main.item_truck_list.view.*

internal class TruckListAdapter(private val context: Context) : RecyclerView.Adapter<TruckListAdapter.TruckHolder>() {
    private val dummy_url: String = "https://f0.pngfuel.com/png/215/167/industry-user-experience-korskyrkan-orebro-lorem-ipsum-is-simply-dummy-text-of-the-printing-chatbot-avatar-png-clip-art.png"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TruckHolder {
        return TruckHolder(LayoutInflater.from(context).inflate(R.layout.item_truck_list,parent,false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: TruckHolder, position: Int) {
        holder.bind(position)
    }

    inner class TruckHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imgUserDp = itemView.img_user_dp
        fun bind(position: Int){
            LimeUtils.setImageUsingPicasso(context,imgUserDp,dummy_url)
        }
    }
}