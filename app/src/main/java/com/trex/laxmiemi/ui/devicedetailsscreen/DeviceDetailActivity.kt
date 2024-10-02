package com.trex.laxmiemi.ui.devicedetailsscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.trex.laxmiemi.data.firebase.firestore.Device
import com.trex.laxmiemi.utils.CommonConstants.SINGLE_DEVICE_DATA
import com.trex.rexcommon.data.SendMessageDto

class DeviceDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm by viewModels<DeviceScreenDetailViewModel>()
        val device = intent.getSerializableExtra(SINGLE_DEVICE_DATA)
        setContent {
            DeviceDetails(device as Device) {
                vm.sendAction(
                    SendMessageDto(
                        device.fcmTokenId,
                        it,
                    ),
                )
            }
        }
    }
}
