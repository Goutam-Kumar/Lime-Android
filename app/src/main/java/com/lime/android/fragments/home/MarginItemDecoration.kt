package com.lime.android.fragments.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal class MarginItemDecoration(private val spaceHeight: Int): RecyclerView.ItemDecoration(){
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                left = 0
            }else{
                left = spaceHeight
            }
            top =  20
            right = 0
            bottom = 0
        }
    }
}
