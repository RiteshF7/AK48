package com.trex.laxmiemi.ui.devicedetailsscreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexnetwork.data.Actions
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.domain.firebasecore.fcm.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeviceFirestore
import com.trex.rexnetwork.domain.firebasecore.firesstore.FCMTokenFirestore
import com.trex.rexnetwork.domain.repositories.DeleteDeviceRepo
import com.trex.rexnetwork.utils.SharedPreferenceManager
import kotlin.random.Random

class DeviceScreenDetailViewModel : ViewModel() {
    private val fcmFirestore = FCMTokenFirestore()
    private val _unlockCodeState = mutableStateOf("Get unlock code")
    val unlockCode: State<String> = _unlockCodeState
    private val _deleteDevice = mutableStateOf(false)
    val deleteDevice: State<Boolean> = _deleteDevice
    val deleteDeviceRepo = DeleteDeviceRepo(CommonConstants.shodId)

    fun refreshFcmBeforeAction(
        fcmTokenManager: FCMTokenManager,
        mSharedPreferenceManager: SharedPreferenceManager,
    ) {
        fcmTokenManager.refreshToken {}
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

    fun deleteDevice(
        device: NewDevice,
        onActionClick: (Actions) -> Unit,
        context: Context,
    ) {
        if (device.deviceLockStatus) {
            Toast
                .makeText(
                    context,
                    "Please unlock device before removing device!",
                    Toast.LENGTH_SHORT,
                ).show()
        } else {
            Toast
                .makeText(context, "Device will be deleted soon!", Toast.LENGTH_SHORT)
                .show()
            onActionClick(Actions.ACTION_REMOVE_DEVICE)
            _deleteDevice.value = true
        }
    }
}
