package com.trex.laxmiemi

import HomeScreen
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.trex.laxmiemi.databinding.ActivityMainBinding
import com.trex.laxmiemi.ui.loginscreen.OtpSendActivity
import com.trex.rexnetwork.domain.firebasecore.fcm.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.fcm.ShopFcmTokenUpdater
import com.trex.rexnetwork.utils.SharedPreferenceManager

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // In Activity's onCreate() for instance
//        val w = window
//        w.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//        )
        val mainActivityViewModel: MainActivityViewModel by viewModels()
        checkIsUserLoggedIn(mainActivityViewModel) {
            setContent {
                MyApp(mainActivityViewModel)
            }
        }
    }

    fun checkIsUserLoggedIn(
        mainViewModel: MainActivityViewModel,
        onSuccess: () -> Unit,
    ) {
        val shopFCMTokenManager = FCMTokenManager(this, ShopFcmTokenUpdater(this))
        val mshardPref = SharedPreferenceManager(this)

        mainViewModel.firebaseUser.observe(this) {
            if (it != null) {
                mainViewModel.checkIfShopExists(
                    shopFCMTokenManager,
                    mshardPref,
                    ShopFcmTokenUpdater(this),
                    onSuccess,
                )
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
