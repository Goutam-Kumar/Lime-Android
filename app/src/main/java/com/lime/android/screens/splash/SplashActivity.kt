package com.lime.android.screens.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lime.android.R
import com.lime.android.screens.login.LoginActivity

private const val TIME_OUT_TIME: Long = 3000
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(R.layout.activity_splash)
        configureHandler()
    }

    private fun configureHandler() {
        Handler().postDelayed(Runnable {
            startActivity(Intent(this,LoginActivity::class.java))
            this.finish()
        }, TIME_OUT_TIME)
    }
}
