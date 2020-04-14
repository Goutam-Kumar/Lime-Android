package com.lime.android.screens.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lime.android.R
import kotlinx.android.synthetic.main.item_drawer_menu.view.*

class DrawerAdapter(private val onAdapterItemClicked: (position: Int) -> Unit, private val context: Context): RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder>() {
    private val menuNames = arrayOf<Int>(R.string.home,R.string.current_booking, R.string.history_booking,R.string.about_us,R.string.termsandcond, R.string.logout)
    private val menuIcon = arrayOf<Int>(R.drawable.ic_home_icon, R.drawable.ic_current_booking_icon,
        R.drawable.ic_history_booking_icon, R.drawable.ic_about_icon,R.drawable.ic_terms_condition_icon,R.drawable.ic_logout_icon)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerViewHolder {
        return DrawerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_drawer_menu,parent,false))
    }

    override fun getItemCount(): Int {
        return menuNames.size
    }

    override fun onBindViewHolder(holder: DrawerViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class DrawerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imgMenuIcon = itemView.img_menu_icon
        private val tvMenuName = itemView.tv_menu_name

        fun bind(position: Int) {
            imgMenuIcon.setImageResource(menuIcon[position])
            tvMenuName.text = context.getText(menuNames[position])
            itemView.setOnClickListener { onAdapterItemClicked(position) }
        }
    }
}
