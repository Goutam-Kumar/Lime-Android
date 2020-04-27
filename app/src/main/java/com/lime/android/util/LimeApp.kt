package com.lime.android.util

import android.app.Application

const val DEBUG_APP = "debug";
const val RELEASE_APP = "release"
class LimeApp: Application() {
    companion object{
        val appSetup: String = DEBUG_APP
    }
}
