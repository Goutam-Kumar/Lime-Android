package com.lime.android.ui.customview.robotoradiobutton

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton
import com.lime.android.R

internal class RobotoRadioButton@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.radioButtonStyle
): AppCompatRadioButton(context,attributeSet,defStyleAttr) {

    init {
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.RobotoRadioButton, 0, 0).apply {
            val fontName = getString(R.styleable.RobotoRadioButton_rd_fontName)
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
