package com.lime.android.ui.navigationui

import androidx.lifecycle.ViewModel

open class NavigationViewModel: ViewModel() {

    val navigationLiveDataField =
        LiveNavigationField<NavigationEvent>()

    fun navigateTo(destination: NavigationDestination) {
        navigationLiveDataField.latestValue = destination.buildEvent()
    }
}
