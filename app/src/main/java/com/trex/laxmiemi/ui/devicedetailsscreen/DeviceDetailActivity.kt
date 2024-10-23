package com.trex.laxmiemi.ui.devicedetailsscreen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.trex.laxmiemi.utils.CommonConstants.SINGLE_DEVICE_DATA
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.data.ActionMessageDTO

class DeviceDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm by viewModels<DeviceScreenDetailViewModel>()
        val device = intent.getParcelableExtra<NewDevice>(SINGLE_DEVICE_DATA)
        setContent {
            device?.let {
                DeviceDetails(device) {
                    val messageData = ActionMessageDTO(device.fcmToken, it)
                    Log.i("TAG", "message sending .... : ${messageData.action}")
                    vm.sendAction(
                        ActionMessageDTO(
                            device.fcmToken,
                            it,
                        ),
                    )
                }
            }
        }
    }
}
