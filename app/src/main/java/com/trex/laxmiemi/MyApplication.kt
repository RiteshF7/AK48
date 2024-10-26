package com.trex.laxmiemi

import android.app.Application
import android.content.IntentFilter
import com.google.firebase.FirebaseApp
import com.trex.laxmiemi.ui.PayloadReceiver
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexnetwork.Constants
import com.trex.rexnetwork.domain.firebasecore.fcm.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.fcm.ShopFcmTokenUpdater

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CommonConstants.initialize(applicationContext)
        FirebaseApp.initializeApp(this)
        initPayloadReceiver()
    }

    private fun initPayloadReceiver() {
        val receiver = PayloadReceiver()
        val filter = IntentFilter("$packageName.${Constants.KEY_BROADCAST_PAYLOAD_ACTION}")
        this.registerReceiver(receiver, filter, RECEIVER_EXPORTED)
    }
}
