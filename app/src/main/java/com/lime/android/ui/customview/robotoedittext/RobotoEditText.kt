package com.lime.android.ui.customview.robotoedittext

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.lime.android.R

internal class RobotoEditText@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle
): AppCompatEditText(context,attributeSet,defStyleAttr) {

    init {
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.RobotoEditText, 0, 0).apply {
            val fontName = getString(R.styleable.RobotoEditText_et_fontName)
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
