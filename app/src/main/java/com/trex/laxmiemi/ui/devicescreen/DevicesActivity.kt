package com.trex.laxmiemi.ui.devicescreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

class DevicesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: DevicesViewModel by viewModels()
        vm.getAllDevices()
        setContent {
            DeviceScreen(vm)
        }
    }
}
