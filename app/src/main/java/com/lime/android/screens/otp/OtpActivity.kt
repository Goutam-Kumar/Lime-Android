package com.lime.android.screens.otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.R
import com.mukesh.OtpView

class OtpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        configureUI()
    }

    private fun configureUI() {
        findViewById<Button>(R.id.btn_continue).apply {
            setOnClickListener { startActivity(Intent(this@OtpActivity,
                MainActivity::class.java)) }
        }
        findViewById<OtpView>(R.id.otp_view).apply {
            setOtpCompletionListener {

            }
        }
    }
}
