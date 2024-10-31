package com.trex.laxmiemi

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.trex.laxmiemi.ui.components.HomeScreen
import com.trex.laxmiemi.ui.createdevicescreen.CreateDeviceActivity
import com.trex.laxmiemi.ui.createdevicescreen.FormData
import com.trex.laxmiemi.ui.loginscreen.OtpSendActivity
import com.trex.rexnetwork.domain.firebasecore.fcm.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.fcm.ShopFcmTokenUpdater
import com.trex.rexnetwork.domain.firebasecore.fcm.fcmrequestscreen.FcmRequestActivity
import com.trex.rexnetwork.utils.SharedPreferenceManager

class MainActivity : ComponentActivity() {
    private lateinit var shopFCMTokenManager: FCMTokenManager
    private lateinit var mshardPref: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shopFCMTokenManager = FCMTokenManager(this, ShopFcmTokenUpdater(this))
        mshardPref = SharedPreferenceManager(this)
        enableEdgeToEdge()
//        CreateDeviceActivity.startCreateDeviceActivity(this, FormData("Ritesh", imeiOne = "123456789012345", deviceModel = "Samsung!"))
        val mainViewModel: MainActivityViewModel by viewModels()
        mainViewModel.firebaseUser.observe(this) {
            if (it != null) {
                mainViewModel.checkIfShopExists(
                    shopFCMTokenManager,
                    mshardPref,
                    ShopFcmTokenUpdater(this),
                ) {
                    setContent {
                        MyApp(mainViewModel)
                    }
                }
            } else {
                val sendOtpActivity = Intent(this, OtpSendActivity::class.java)
                startActivity(sendOtpActivity)
                finish()
            }
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
