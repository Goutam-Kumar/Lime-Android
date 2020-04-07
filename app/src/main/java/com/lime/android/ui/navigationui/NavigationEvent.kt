package com.lime.android.ui.navigationui

import android.os.Bundle
import androidx.annotation.IdRes

data class NavigationEvent(@IdRes val navId: Int, val navigationArguments: NavigationArguments? = null) {

    fun argumentsBundle(): Bundle? = navigationArguments?.asBundle()
}
