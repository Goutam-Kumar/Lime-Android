package com.lime.android.ui.customview.robotobutton

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.lime.android.R

internal class RobotoButton@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int =  R.attr.buttonStyle
): AppCompatButton(context,attributeSet,defStyleAttr) {

    init {
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.RobotoButton, 0, 0).apply {
            val fontName = getString(R.styleable.RobotoButton_bt_fontName)
            try {
                if (fontName != null) {
                    val myTypeface = Typeface.createFromAsset(context.assets, "fonts/" + fontName.orEmpty())
                    typeface = myTypeface
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
        }
    }


}
