package com.lime.android.fragments.additionaldetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.lime.android.R
import com.lime.android.models.vehicleandgoods.GoodsType

internal class GoodsAdapter(val context: Context, var listItemsTxt: List<GoodsType>): BaseAdapter() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.view_drop_down_menu, parent, false)
            vh = ItemRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        vh.label.text = listItemsTxt[position].name
        return view
    }

    override fun getItem(position: Int): GoodsType {
        return listItemsTxt[position]
    }

    override fun getItemId(position: Int): Long {
        return listItemsTxt[position].id.toLong()

    }

    override fun getCount(): Int {
        return listItemsTxt.size
    }

    private class ItemRowHolder(row: View?) {
        val label: TextView = row?.findViewById(R.id.txtDropDownLabel) as TextView
    }
}
