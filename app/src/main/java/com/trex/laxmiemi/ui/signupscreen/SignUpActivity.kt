package com.trex.laxmiemi.ui.signupscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        val vm by viewModels<SignUpViewModel>()
        setContent {
            SignUpScreen()
        }
    }
}
