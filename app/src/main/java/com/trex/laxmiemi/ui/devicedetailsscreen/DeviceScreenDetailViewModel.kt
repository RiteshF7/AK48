package com.trex.laxmiemi.ui.devicedetailsscreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.domain.firebasecore.fcm.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeviceFirestore
import com.trex.rexnetwork.domain.firebasecore.firesstore.FCMTokenFirestore
import com.trex.rexnetwork.utils.SharedPreferenceManager
import kotlin.random.Random

class DeviceScreenDetailViewModel : ViewModel() {
    private val fcmFirestore = FCMTokenFirestore()
    private val _unlockCodeState = mutableStateOf("Get unlock code")
    val unlockCode: State<String> = _unlockCodeState

    fun refreshFcmBeforeAction(
        fcmTokenManager: FCMTokenManager,
        mSharedPreferenceManager: SharedPreferenceManager,
    ) {
        mSharedPreferenceManager.getShopId()?.let { shopId ->
            fcmFirestore.getFcmToken(shopId) { token ->
                fcmTokenManager.refreshToken(token)
            }
        }
    }

    fun generateUnlockCode(
        shopId: String,
        deviceId: String,
    ) {
        val unlockCode = Random.nextInt(10000, 100000).toString()
        val deviceRepo = DeviceFirestore(shopId)
        deviceRepo.updateSingleField(deviceId, NewDevice::unlockCode.name, unlockCode, {
            _unlockCodeState.value = unlockCode
        }, { error ->
            Log.i("", "update failed :: $error")
        })
    }
}
