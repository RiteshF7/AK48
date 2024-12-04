package com.trex.laxmiemi.ui.loginscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

class LoginWithEmailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        val vm by viewModels<LoginViewModel>()
        setContent {
            LoginScreen(vm) {
            }
        }
    }
}
