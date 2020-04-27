package com.lime.android.ui.customview.emptybox

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.lime.android.R
import com.lime.android.util.LimeUtils

class EmptyBox@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attributeSet, defStyleAttr) {
    private val emptyBox: TextView
    private val imageView: ImageView

    init {
        View.inflate(context, R.layout.empty_box_layout, this)
        emptyBox = findViewById(R.id.tv_empty_text)
        imageView = findViewById(R.id.imageView)
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.EmptyBox,0, 0).apply {
            emptyBox.text =getString(R.styleable.EmptyBox_emptyText)
            LimeUtils.shakeAnimation(context,imageView)
        }
    }

    fun setEmptyText(text: String){
        emptyBox.text = text
    }


}
