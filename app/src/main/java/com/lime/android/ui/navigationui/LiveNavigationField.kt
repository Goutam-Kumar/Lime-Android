package com.lime.android.ui.navigationui

import androidx.lifecycle.*


class LiveNavigationField<T : Any> : LifecycleObserver {
    private var liveData = MutableLiveData<T>()

    private var lifecycleOwner: LifecycleOwner? = null

    var latestValue: T?
        get() = liveData.value
        set(value) {
            liveData.value = value
        }


    fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        check(lifecycleOwner == null) { "Function 'observe' can only be called once during each lifecycle" }

        lifecycleOwner = owner.also {
            it.lifecycle.addObserver(this)
        }

        liveData.observe(owner, observer)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        lifecycleOwner?.let {
            lifecycleOwner = null
            liveData = MutableLiveData()
            it.lifecycle.removeObserver(this)
        }
    }
}
