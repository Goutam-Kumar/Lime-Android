package com.lime.android.ui.navigationui


open class NavigationDestination(private val id: Int, private val navigationArguments: NavigationArguments? = null) {

    fun buildEvent(): NavigationEvent =
        NavigationEvent(id, navigationArguments)
}
