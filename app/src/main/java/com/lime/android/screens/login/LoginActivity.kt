package com.lime.android.screens.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.lime.android.R
import com.lime.android.screens.otp.OtpActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        configureUI()
    }

    private fun configureUI() {
        findViewById<Button>(R.id.btn_login).apply {
            setOnClickListener { startActivity(Intent(this@LoginActivity,
                OtpActivity::class.java)) }
        }
    }
}
