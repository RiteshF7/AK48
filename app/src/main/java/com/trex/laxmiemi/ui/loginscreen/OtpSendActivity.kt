package com.trex.laxmiemi.ui.loginscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.trex.laxmiemi.databinding.ActivityOtpSendBinding
import com.trex.laxmiemi.ui.signupscreen.SignUpActivity
import java.util.concurrent.TimeUnit

class OtpSendActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "PhoneAuthActivity"
    }

    private lateinit var binding: ActivityOtpSendBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mResendingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mVerificationId: String

    override fun onStart() {
        super.onStart()
//        if (mAuth.currentUser != null) {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpSendBinding.inflate(layoutInflater)
        startActivity(Intent(this,SignUpActivity::class.java))
        // In Activity's onCreate() for instance
//        val w = window
//        w.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//        )
//        setContentView(binding.root)
//
//        mAuth = Firebase.auth
//
//        binding.btnSend.setOnClickListener {
//            val number =
//                binding.etPhone.text
//                    .toString()
//                    .trim()
//            val countryCode = binding.ccp.selectedCountryCodeWithPlus
//            val phoneNumber = getPhoneNumber(number)
//
//            otpSend("$countryCode$phoneNumber")
//        }
    }

    private fun getPhoneNumber(phone: String): String? {
        if (phone.isEmpty()) {
            Callback(this).onToast("Phone number is required!")
        } else if (phone.length > 10) {
            Callback(this).onToast("Need valid phone number!")
        } else {
            return if (phone.substring(0, 1) == "0") phone.substring(1) else phone
        }

        return null
    }

    private fun otpSend(phoneNumberWithCountryCode: String) {
        isVisible(true)

        mCallbacks =
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Log.d(TAG, "onVerificationCompleted: ${credential.smsCode}")
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    isVisible(false)
                    Callback(this@OtpSendActivity).onToast(e.localizedMessage!!)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken,
                ) {
//                super.onCodeSent(verificationId, token)
                    isVisible(false)
                    Callback(this@OtpSendActivity).onToast("OTP is successfully send.")
                    mResendingToken = token
                    mVerificationId = verificationId

                    startActivity(
                        Intent(
                            this@OtpSendActivity,
                            OtpVerifyActivity::class.java,
                        ).putExtra("phone", phoneNumberWithCountryCode)
                            .putExtra("verificationId", mVerificationId)
                            .putExtra("forceResendingToken", mResendingToken),
                    )
                }
            }

        PhoneAuthProvider.verifyPhoneNumber(
            PhoneAuthOptions
                .newBuilder(mAuth)
                .setPhoneNumber("$phoneNumberWithCountryCode")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build(),
        )
    }

    private fun isVisible(visible: Boolean) {
        binding.progressBar.isVisible = visible
        binding.btnSend.isVisible = !visible
    }
}
