package com.trex.laxmiemi

import android.app.Application
import android.content.IntentFilter
import com.trex.laxmiemi.ui.PayloadReceiver
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexnetwork.Constants

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CommonConstants.initialize(applicationContext)
        initPayloadReceiver()
    }

    private fun initPayloadReceiver() {
        val receiver = PayloadReceiver()
        val filter = IntentFilter("$packageName.${Constants.KEY_BROADCAST_PAYLOAD_ACTION}")
        this.registerReceiver(receiver, filter)
    }
}
