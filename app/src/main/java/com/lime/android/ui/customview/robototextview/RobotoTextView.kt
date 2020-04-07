package com.lime.android.ui.customview.robototextview

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.lime.android.R

internal class RobotoTextView@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
): AppCompatTextView(context, attributeSet, defStyleAttr) {

    init {
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.RobotoTextView, 0, 0).apply {
            val fontName = getString(R.styleable.RobotoTextView_tv_fontName)
            try {
                if (fontName != null) {
                    val myTypeface = Typeface.createFromAsset(context.assets, "fonts/" + fontName.orEmpty())
                    typeface = myTypeface
                    Log.d("Typeface",myTypeface.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
        }
    }
}
