package com.lime.android.screens.otp

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.lime.android.R
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.sharedrepository.LimeSharedRepositoryImpl
import com.lime.android.util.GLOBAL_TAG
import com.lime.android.util.LimeUtils
import com.lime.android.util.PHONE_NUMBER
import com.mukesh.OtpView
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this, LimeUtils.viewModelFactory { OtpViewModel(this) }).get(
            OtpViewModel::class.java)
    }
    private var enteredMobileNumber: String? = null
    private var enteredOtp: String? = null
    private lateinit var verificationCodeBySystem: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        configureUI()
        enteredMobileNumber = intent.getStringExtra(PHONE_NUMBER)
        sendVerificationCodeToUser(enteredMobileNumber)
        setViewModelObservers()
    }

    private fun setViewModelObservers() {
        viewModel.run {
            otpError.observe(this@OtpActivity, Observer { LimeUtils.makeToast(this@OtpActivity,it) })
            progress.observe(this@OtpActivity, Observer { if (it) signInWithPhoneAuthCredential(enteredOtp) })
        }
    }

    private fun configureUI() {
        findViewById<Button>(R.id.btn_continue).apply {
            setOnClickListener {
                viewModel.validate(enteredOtp)
            }
        }
        findViewById<OtpView>(R.id.otp_view).apply {
            setOtpCompletionListener {
                enteredOtp = it
            }
        }
        findViewById<ImageView>(R.id.img_back).setOnClickListener { finish() }
    }

    private fun sendVerificationCodeToUser(enteredMobileNumber: String?) {
        enteredMobileNumber.let {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                enteredMobileNumber.orEmpty(),
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks
            )
        }
    }

    private var mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onCodeSent(code: String, p1: PhoneAuthProvider.ForceResendingToken) {
            verificationCodeBySystem = code
        }
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(GLOBAL_TAG, "onVerificationCompleted:$credential")
            findViewById<OtpView>(R.id.otp_view).text = SpannableStringBuilder(credential.smsCode)
            signInWithPhoneAuthCredential(credential.smsCode)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.e(GLOBAL_TAG, "onVerificationFailed", e)
            when (e) {
                is FirebaseAuthInvalidCredentialsException -> LimeUtils.makeToast(this@OtpActivity,e.message.orEmpty())
                is FirebaseTooManyRequestsException -> LimeUtils.makeToast(this@OtpActivity,e.message.orEmpty())
                else -> LimeUtils.makeToast(this@OtpActivity,e.message.orEmpty())
            }
        }
    }

    private fun signInWithPhoneAuthCredential(codeByUser: String?) {
        codeByUser?.let {
            val credential = PhoneAuthProvider.getCredential(verificationCodeBySystem,codeByUser)
            signInUserByCredential(credential)
        }
    }

    private fun signInUserByCredential(credential: PhoneAuthCredential) {
        val fireBaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        fireBaseAuth.signInWithCredential(credential).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful){
                LimeSharedRepositoryImpl(this).isLoggedIn = true
                startActivity(Intent(this@OtpActivity, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
            else
                LimeUtils.makeToast(this@OtpActivity,task.exception?.message.orEmpty())
        }
    }
}
