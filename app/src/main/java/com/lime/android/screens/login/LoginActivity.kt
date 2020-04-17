package com.lime.android.screens.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.lime.android.R
import com.lime.android.screens.otp.OtpActivity
import com.lime.android.screens.register.RegisterActivity
import com.lime.android.sharedrepository.LimeSharedRepositoryImpl
import com.lime.android.util.GLOBAL_TAG
import com.lime.android.util.LimeUtils
import com.lime.android.util.PHONE_NUMBER


class LoginActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this, LimeUtils.viewModelFactory { LoginViewModel(this) }).get(LoginViewModel::class.java)
    }
    private lateinit var etMobileNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        configureUI()
        setViewModelObservers()
    }

    init {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(GLOBAL_TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result?.token
                LimeSharedRepositoryImpl(this).fcmToken = token
                Log.d(GLOBAL_TAG, token)
            })
    }

    private fun setViewModelObservers() {
        viewModel.run {
            mobileError.observe(this@LoginActivity, Observer {
                etMobileNumber.error = it
            })
            progress.observe(this@LoginActivity, Observer { if (it)
                startActivity(Intent(this@LoginActivity, OtpActivity::class.java).putExtra(
                    PHONE_NUMBER, findViewById<TextView>(R.id.tv_std_code).text.toString().plus(etMobileNumber.text.toString())
                ))
            })
            spinner.observe(this@LoginActivity, Observer { handleSpinner(it) })
            serviceException.observe(this@LoginActivity, Observer { showServiceError(it) })
        }
    }

    private fun handleSpinner(isVisible: Boolean) {
        //TODO has to be implement
    }

    private fun showServiceError(message: String?) {
        message?.let {
            val msg = it
            AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok) { _, _ ->  }
                .create().run {
                    setCanceledOnTouchOutside(false)
                    show()
                }
        }
    }

    private fun configureUI() {
        findViewById<Button>(R.id.btn_login).apply {
            setOnClickListener { viewModel.validateFields() }
        }
        findViewById<TextView>(R.id.tv_signup).setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        etMobileNumber = findViewById<EditText>(R.id.et_mobile_number)
        etMobileNumber.addTextChangedListener{
            viewModel.onMobileNumberChanged(it.toString())
        }
    }

}
