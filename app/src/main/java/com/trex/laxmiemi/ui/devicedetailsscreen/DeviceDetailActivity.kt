package com.trex.laxmiemi.ui.devicedetailsscreen

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.trex.laxmiemi.utils.CommonConstants.SINGLE_DEVICE_DATA
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.domain.firebasecore.fcm.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.fcm.ShopFcmTokenUpdater
import com.trex.rexnetwork.utils.SharedPreferenceManager

class DeviceDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm by viewModels<DeviceScreenDetailViewModel>()
        val device = intent.getParcelableExtra<NewDevice>(SINGLE_DEVICE_DATA)
        val fcmManager = FCMTokenManager(this, ShopFcmTokenUpdater(this))
        val mSharedPreferenceManager = SharedPreferenceManager(this)
        vm.refreshFcmBeforeAction(fcmManager, mSharedPreferenceManager)
        setContent {
            device?.let {
                DeviceDetails(device) {
                    val messageData = ActionMessageDTO(device.fcmToken, it)
                    Log.i("TAG", "message sending .... : ${messageData.action}")
                    Toast.makeText(this, "Completed!!", Toast.LENGTH_SHORT).show()
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
