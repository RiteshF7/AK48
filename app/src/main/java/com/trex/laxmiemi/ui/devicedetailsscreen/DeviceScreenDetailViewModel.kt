package com.trex.laxmiemi.ui.devicedetailsscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trex.laxmiemi.ui.devicescreen.DevicesViewModel.Companion.TAG
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.domain.firebasecore.fcm.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeviceFirestore
import com.trex.rexnetwork.domain.firebasecore.firesstore.FCMTokenFirestore
import com.trex.rexnetwork.domain.repositories.SendActionMessageRepository
import com.trex.rexnetwork.utils.SharedPreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeviceScreenDetailViewModel : ViewModel() {
    private val repo = SendActionMessageRepository()

    private val _devices = MutableLiveData<NewDevice>()
    val devices: LiveData<NewDevice> = _devices
    private val devicesFirestore = DeviceFirestore(CommonConstants.shodId)
    private val fcmFirestore = FCMTokenFirestore()

    fun getAllDevices() {
        devicesFirestore.getSingleDevice("123456789", {
            _devices.value = it
        }, {
            Log.i(TAG, "getAllDevices: error getting device!!")
        })
    }

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


}
