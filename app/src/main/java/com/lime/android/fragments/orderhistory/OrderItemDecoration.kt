package com.lime.android.fragments.orderhistory

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal class OrderItemDecoration(private val spaceHeight: Int): RecyclerView.ItemDecoration(){
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            top = if (parent.getChildLayoutPosition(view) == 0){
                spaceHeight
            }else{
                0
            }
            left =  0
            right = 0
            bottom = spaceHeight
        }
    }
}
