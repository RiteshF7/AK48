package com.trex.rexandroidsecureclient

import IMEIHelper
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.trex.rexandroidsecureclient.ui.theme.LaxmiEmiTheme
import com.trex.rexandroidsecureclient.utils.DevicePolicyHelper

class MainActivity : ComponentActivity() {
    private lateinit var imeiHelper: IMEIHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        imeiHelper = IMEIHelper(this)
        FirebaseApp.initializeApp(this)
        val vm by viewModels<MainActivityViewModel>()
        setContent {
            LaxmiEmiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        Box(
                            Modifier
                                .align(Alignment.Center)
                                .padding(10.dp),
                        ) {
                            GetTokenButton(vm = vm, imeiHelper)
                        }
                    }
                }
            }
        }
    }

    private fun checkIfDeviceOwner() {
        val manager =
            this.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        Log.i("Main Activity", "checkIfDeviceOwner: ${manager.isDeviceOwnerApp(this.packageName)}")
    }
}

@Composable
fun GetTokenButton(
    vm: MainActivityViewModel,
    imeiHelper: IMEIHelper,
) {
    val context = LocalContext.current
    val token = vm.currentToken.observeAsState("See token")
    val imei = vm.deviceImei.observeAsState("See token")
    Button(
        onClick = {
            try {
//                vm.getCurrentToken()
//                vm.getDeviceImei(imeiHelper)
                DevicePolicyHelper(context).lockDevice();
            } catch (e: Exception) {
                Log.e("FCM", "Error saving token", e)
            }
        },
    ) {
        Text(text = imei.value, modifier = Modifier.padding(20.dp))
    }
}
