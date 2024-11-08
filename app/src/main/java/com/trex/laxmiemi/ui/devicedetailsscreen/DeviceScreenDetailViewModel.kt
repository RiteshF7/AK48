package com.trex.laxmiemi.ui.devicedetailsscreen

import androidx.lifecycle.ViewModel
import com.trex.rexnetwork.domain.firebasecore.fcm.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.firesstore.FCMTokenFirestore
import com.trex.rexnetwork.utils.SharedPreferenceManager

class DeviceScreenDetailViewModel : ViewModel() {
    private val fcmFirestore = FCMTokenFirestore()

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
