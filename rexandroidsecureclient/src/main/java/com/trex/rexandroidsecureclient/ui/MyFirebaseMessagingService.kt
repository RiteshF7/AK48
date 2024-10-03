package com.trex.rexandroidsecureclient.ui

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.trex.rexcommon.data.NewDevice
import com.trex.rexcommon.data.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.data.isNotEmpty().let {
            println("Message data payload: ${remoteMessage.data}")
        }

        // Handle notification payload of FCM messages
        remoteMessage.notification?.let {
            println("Message Notification Body: ${it.body}")
            // Show notification
        }
    }

    override fun onNewToken(token: String) {
        val newDevice = NewDevice(imeiOne = 1234567890, fcmToken = token)
        sendRegistrationToServer(newDevice)
    }

    private fun sendRegistrationToServer(device: NewDevice) {
        scope.launch {
            RetrofitClient.builder.registerNewDevice(device)
        }
    }
}
