package com.trex.laxmiemi

import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.trex.rexnetwork.domain.firebasecore.fcm.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.fcm.ShopFcmTokenUpdater

class MainActivity : ComponentActivity() {
    private lateinit var tokenManager: FCMTokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tokenManager = FCMTokenManager(this, ShopFcmTokenUpdater(this))
        tokenManager.refreshToken {}
        val mainActivityViewModel: MainActivityViewModel by viewModels()
        setContent {
            MyApp(mainActivityViewModel)
        }
    }
}

@Composable
fun MyApp(mainViewModel: MainActivityViewModel) {
    Scaffold(
        content = { padding ->
            Box(Modifier.padding(padding)) {
                HomeScreen(mainViewModel)
            }
        },
    )
}
