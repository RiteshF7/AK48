package com.trex.laxmiemi

import android.app.Application
import android.content.IntentFilter
import android.util.Log
import com.trex.laxmiemi.ui.PayloadReceiver
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexnetwork.Constants
import com.trex.rexnetwork.utils.FirebaseInstallationManager
import com.trex.rexnetwork.utils.PeriodicWorkManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CommonConstants.initialize(applicationContext)
        val installationManager = FirebaseInstallationManager()
        installationManager.initializeFirebase(applicationContext) { success ->
            if (success) {
                PeriodicWorkManager.startPeriodicWork(applicationContext)

                Log.i("Firebase", "onCreate: Firebase initilized successfully!")
            } else {
                Log.e("Firebase error", "onCreate: Error in initilizing firebase!")
            }
        }
        initPayloadReceiver()
    }

    private fun initPayloadReceiver() {
        val receiver = PayloadReceiver()
        val filter = IntentFilter("$packageName.${Constants.KEY_BROADCAST_PAYLOAD_ACTION}")
        this.registerReceiver(receiver, filter, RECEIVER_EXPORTED)
    }
}
