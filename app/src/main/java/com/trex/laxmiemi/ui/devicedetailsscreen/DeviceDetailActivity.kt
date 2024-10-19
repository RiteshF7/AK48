package com.trex.laxmiemi.ui.devicedetailsscreen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.trex.laxmiemi.utils.CommonConstants.SINGLE_DEVICE_DATA
import com.trex.rexcommon.data.NewDevice
import com.trex.rexcommon.data.SendMessageDto

class DeviceDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm by viewModels<DeviceScreenDetailViewModel>()
        val device = intent.getParcelableExtra<NewDevice>(SINGLE_DEVICE_DATA)
        setContent {
            device?.let {
                DeviceDetails(device) {
                    val messageData = SendMessageDto(device.fcmToken, it)
                    Log.i("TAG", "message sending .... : ${messageData.action}")
                    vm.sendAction(
                        SendMessageDto(
                            device.fcmToken,
                            it,
                        ),
                    )
                }
            }
        }
    }
}
