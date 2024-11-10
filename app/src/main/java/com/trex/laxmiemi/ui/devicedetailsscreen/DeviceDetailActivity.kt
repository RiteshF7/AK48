package com.trex.laxmiemi.ui.devicedetailsscreen

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.trex.laxmiemi.handlers.ShopActionExecutor
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.domain.firebasecore.fcm.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.fcm.ShopFcmTokenUpdater
import com.trex.rexnetwork.utils.SharedPreferenceManager
import com.trex.rexnetwork.utils.getExtraData
import com.trex.rexnetwork.utils.startMyActivity

class DeviceDetailActivity : ComponentActivity() {
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        val vm by viewModels<DeviceScreenDetailViewModel>()
        val device = intent.getExtraData<NewDevice>()
        val fcmManager = FCMTokenManager(this, ShopFcmTokenUpdater(this))
        val mSharedPreferenceManager = SharedPreferenceManager(this)
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

    companion object {
        fun go(
            context: Context,
            device: NewDevice,
        ) {
            context.startMyActivity(
                DeviceDetailActivity::class.java,
                device,
                false,
            )
        }
    }
}
