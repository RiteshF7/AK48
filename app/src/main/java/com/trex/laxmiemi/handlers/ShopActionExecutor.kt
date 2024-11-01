package com.trex.laxmiemi.handlers

import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.trex.laxmiemi.ui.actionresultscreen.ActionResultActivity
import com.trex.laxmiemi.ui.createdevicescreen.CreateDeviceActivity
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.Actions.ACTION_GET_CONTACTS
import com.trex.rexnetwork.data.Actions.ACTION_GET_CONTACTS_VIA_MESSAGE
import com.trex.rexnetwork.data.Actions.ACTION_GET_DEVICE_INFO
import com.trex.rexnetwork.data.Actions.ACTION_GET_LOCATION
import com.trex.rexnetwork.data.Actions.ACTION_GET_LOCATION_VIA_MESSAGE
import com.trex.rexnetwork.data.Actions.ACTION_GET_PHONE_NUMBER
import com.trex.rexnetwork.data.Actions.ACTION_GET_UNLOCK_CODE
import com.trex.rexnetwork.data.Actions.ACTION_REG_DEVICE
import com.trex.rexnetwork.domain.firebasecore.fcm.fcmrequestscreen.FcmRequestActivity
import com.trex.rexnetwork.domain.firebasecore.fcm.fcmrequestscreen.FcmResponseManager
import com.trex.rexnetwork.domain.repositories.SendActionMessageRepository
import com.trex.rexnetwork.utils.isGetRequest
import com.trex.rexnetwork.utils.startMyActivity

class ShopActionExecutor(
    private val context: Context,
) {
    private val sendActionRepo = SendActionMessageRepository()

    fun sendAction(action: ActionMessageDTO) {
        context.startMyActivity(
            FcmRequestActivity::class.java,
            action,
        )
    }

    fun receiveResponse(response: ActionMessageDTO) {
        val action = response.action
        // change the fcm request activity to process activity
        FcmResponseManager.handleResponse(response.requestId, response)
        if (action.isGetRequest()) {
            when {
                action == ACTION_GET_PHONE_NUMBER -> TODO()
                action == ACTION_GET_CONTACTS -> TODO()
                action == ACTION_GET_CONTACTS_VIA_MESSAGE -> TODO()
                action == ACTION_GET_DEVICE_INFO -> TODO()
                action == ACTION_GET_UNLOCK_CODE -> TODO()
                action == ACTION_GET_LOCATION -> TODO()
                action == ACTION_GET_LOCATION_VIA_MESSAGE -> TODO()
            }
        } else {
            //todo start some activity that can show logp success message and failure message
            //get message from payload and show it to user
            startResultActivity(context, response)
            return
        }
    }

    // don't touch this now!!
    fun receiveAction(message: ActionMessageDTO) {
        when {
            message.action == ACTION_REG_DEVICE -> {
                context.startMyActivity(CreateDeviceActivity::class.java, message)
            }
        }
    }

    fun sendResponse(response: ActionMessageDTO) {
        sendActionRepo.sendActionMessage(response)
    }

    private fun startResultActivity(
        context: Context,
        messageDTO: ActionMessageDTO,
    ) {
        val dtoString = Gson().toJson(messageDTO)
        val intent =
            Intent(context, ActionResultActivity::class.java).apply {
                putExtra(ActionResultActivity.ACTION_RESULT_INTENT_KEY, dtoString)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        context.startActivity(intent)
    }
}
