package com.lime.android.screens.register

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lime.android.R
import com.lime.android.screens.login.LoginActivity
import com.lime.android.screens.otp.OtpActivity
import com.lime.android.util.LimeUtils
import com.lime.android.util.PHONE_NUMBER

class RegisterActivity: AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword : EditText
    private lateinit var etConfPassword: EditText
    private lateinit var radioGroup : RadioGroup

    private val viewModel by lazy {
        ViewModelProviders.of(this, LimeUtils.viewModelFactory { RegisterViewModel(this) }).get(
            RegisterViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        configureUI()
        setViewModelObservers()
    }

    private fun setViewModelObservers() {
        viewModel.run {
            serviceException.observe(this@RegisterActivity, Observer { showServiceError(it) })
            phoneError.observe(this@RegisterActivity, Observer { etPhone.error = it })
            nameError.observe(this@RegisterActivity, Observer { etName.error = it })
            emailError.observe(this@RegisterActivity, Observer { etEmail.error = it })
            passwordError.observe(this@RegisterActivity, Observer { etPassword.error = it })
            confPasswordError.observe(this@RegisterActivity, Observer { etConfPassword.error = it })
            progress.observe(this@RegisterActivity, Observer { if (it)
                startActivity(
                    Intent(this@RegisterActivity, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            })
        }
    }

    private fun configureUI() {
        findViewById<Button>(R.id.btn_submit).setOnClickListener{
            etName.error = null
            etEmail.error = null
            etPassword.error = null
            etPhone.error = null
            etConfPassword.error = null
            viewModel.validateFields()
        }
        findViewById<CheckBox>(R.id.check_tnc).setOnCheckedChangeListener { _, b ->
            viewModel.tnc = b
        }
         etName = findViewById<EditText>(R.id.et_name)
         etPhone = findViewById<EditText>(R.id.et_phone)
         etEmail = findViewById<EditText>(R.id.et_email)
         etPassword = findViewById<EditText>(R.id.et_password)
         etConfPassword = findViewById<EditText>(R.id.et_conf_password)
         radioGroup = findViewById<RadioGroup>(R.id.rdg_customer)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            viewModel.custType = radioButton.text.toString()
        }
        etName.addTextChangedListener{
            viewModel.name = it.toString()
        }
        etPhone.addTextChangedListener{
            viewModel.phone = it.toString()
        }
        etEmail.addTextChangedListener{
            viewModel.email = it.toString()
        }
        etPassword.addTextChangedListener{
            viewModel.password = it.toString()
        }
        etConfPassword.addTextChangedListener{
            viewModel.confPassword = it.toString()
        }
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
}
