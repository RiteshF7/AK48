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
import androidx.lifecycle.lifecycleScope
import com.trex.laxmiemi.utils.AppUpdateUtil
import com.trex.rexnetwork.domain.firebasecore.fcm.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.fcm.ShopFcmTokenUpdater
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var tokenManager: FCMTokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val updateUtil = AppUpdateUtil()
        //initAppUpdate(updateUtil)
        tokenManager = FCMTokenManager(this, ShopFcmTokenUpdater(this))
        tokenManager.refreshToken {}
        val mainActivityViewModel: MainActivityViewModel by viewModels()
        setContent {
            MyApp(mainActivityViewModel)
        }
    }

    private fun initAppUpdate(updateUtil: AppUpdateUtil) {
        lifecycleScope.launch {
            if (updateUtil.checkForUpdates()) {
                updateUtil.showUpdateDialog(this@MainActivity) {
                    lifecycleScope.launch {
                        val apkFile =
                            updateUtil.downloadApk(
                                this@MainActivity,
                                "https://example.com/update.apk",
                            )
                        if (apkFile != null) {
                            updateUtil.installApk(this@MainActivity, apkFile)
                        }
                    }
                }
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
