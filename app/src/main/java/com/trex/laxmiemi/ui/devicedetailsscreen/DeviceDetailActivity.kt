package com.trex.laxmiemi.ui.devicedetailsscreen

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.trex.laxmiemi.handlers.ShopActionExecutor
import com.trex.laxmiemi.utils.CommonConstants.SINGLE_DEVICE_DATA
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.domain.firebasecore.fcm.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.fcm.ShopFcmTokenUpdater
import com.trex.rexnetwork.utils.SharedPreferenceManager

class DeviceDetailActivity : ComponentActivity() {
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm by viewModels<DeviceScreenDetailViewModel>()
        // todo use intent extension function instead
        val device = intent.getParcelableExtra<NewDevice>(SINGLE_DEVICE_DATA)
        val fcmManager = FCMTokenManager(this, ShopFcmTokenUpdater(this))
        val mSharedPreferenceManager = SharedPreferenceManager(this)
        // todo check why is shared pref needed as a func param if i am passing context in class constructor
        vm.refreshFcmBeforeAction(fcmManager, mSharedPreferenceManager)

        setContent {
            device?.let {
                DeviceDetails(device) {
                    // todo add payload in case of some lock list of apps type
                    val message = ActionMessageDTO(device.fcmToken, it)
                    ShopActionExecutor(context).sendActionToClient(message)
                }
            }
        }
    }
}
