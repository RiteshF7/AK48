package com.trex.laxmiemi.ui.signupscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.Black.copy(alpha = 0.85f)),
            ) {
                SignUpScreen {
                    finish()
                }
            }
        }
    }
}
