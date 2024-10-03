package com.trex.rexandroidsecureclient

import DeviceInfoUtil
import IMEIHelper
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.trex.rexandroidsecureclient.ui.theme.LaxmiEmiTheme

class MainActivity : ComponentActivity() {
    private lateinit var imeiHelper: IMEIHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        imeiHelper = IMEIHelper(this)
        FirebaseApp.initializeApp(this)
        val vm by viewModels<MainActivityViewModel>()

//        val deviceInfo = DeviceInfoUtil(this)
//        val allInfo = deviceInfo.getAllDeviceInfo()
//        Log.i("something!!", "onCreate: $allInfo")
        val deviceInfoUtils = DeviceInfoUtil(this)
        Toast.makeText(this, "working activiyt", Toast.LENGTH_SHORT).show()

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
}

@Composable
fun GetTokenButton(
    vm: MainActivityViewModel,
    imeiHelper: IMEIHelper,
) {
    val token = vm.currentToken.observeAsState("See token")
    val imei = vm.deviceImei.observeAsState("See token")
    Button(
        onClick = {
            try {
//                vm.getCurrentToken()
                vm.getDeviceImei(imeiHelper)
            } catch (e: Exception) {
                Log.e("FCM", "Error saving token", e)
            }
        },
    ) {
        Text(text = imei.value, modifier = Modifier.padding(20.dp))
    }
}
