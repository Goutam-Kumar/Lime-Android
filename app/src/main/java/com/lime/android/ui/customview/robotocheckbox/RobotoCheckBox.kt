package com.lime.android.ui.customview.robotocheckbox

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatEditText
import com.lime.android.R

internal class RobotoCheckBox@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.checkboxStyle
): AppCompatCheckBox(context,attributeSet,defStyleAttr) {

    init {
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.RobotoCheckBox, 0, 0).apply {
            val fontName = getString(R.styleable.RobotoCheckBox_ch_fontName)
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
