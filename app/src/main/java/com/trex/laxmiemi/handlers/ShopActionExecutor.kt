package com.trex.laxmiemi.handlers

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trex.laxmiemi.ui.actionresultscreen.ContactResultActivity
import com.trex.laxmiemi.ui.actionresultscreen.ResponseMap
import com.trex.laxmiemi.ui.actionresultscreen.ResponseMapDetailsActivity
import com.trex.laxmiemi.ui.createdevicescreen.CreateDeviceActivity
import com.trex.laxmiemi.utils.GoogleMapUtils
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.Actions.ACTION_GET_CONTACTS
import com.trex.rexnetwork.data.Actions.ACTION_GET_DEVICE_INFO
import com.trex.rexnetwork.data.Actions.ACTION_GET_LOCATION
import com.trex.rexnetwork.data.Actions.ACTION_GET_PHONE_NUMBER
import com.trex.rexnetwork.data.Actions.ACTION_REG_DEVICE
import com.trex.rexnetwork.domain.firebasecore.fcm.fcmrequestscreen.FcmRequestActivity
import com.trex.rexnetwork.domain.firebasecore.fcm.fcmrequestscreen.FcmResultActivity
import com.trex.rexnetwork.domain.repositories.SendActionMessageRepository
import com.trex.rexnetwork.utils.isGetRequest
import com.trex.rexnetwork.utils.startMyActivity

class ShopActionExecutor(
    private val context: Context,
) {
    private val sendActionRepo = SendActionMessageRepository()

    fun sendActionToClient(action: ActionMessageDTO) {
        context.startMyActivity(
            FcmRequestActivity::class.java,
            action,
        )
    }

    fun receiveResponseFromClient(response: ActionMessageDTO) {
        val action = response.action
        if (action.isGetRequest()) {
            when {
                action == ACTION_GET_PHONE_NUMBER -> {
                }

                action == ACTION_GET_CONTACTS -> {
                    context.startMyActivity(ContactResultActivity::class.java, response)
                }

                action == ACTION_GET_DEVICE_INFO -> {
                    response.payload[response.action.name]?.let { payloadString ->
                        val type = object : TypeToken<Map<String, String>>() {}.type
                        val payloadMap: Map<String, String> = Gson().fromJson(payloadString, type)
                        context.startMyActivity(
                            ResponseMapDetailsActivity::class.java,
                            ResponseMap(payloadMap),
                        )
                    }
                }

                action == ACTION_GET_LOCATION -> {
                    handleMapResponse(response, context)
                }

            }
        } else {
            context.startMyActivity(FcmResultActivity::class.java, response)
        }
    }

    private fun handleMapResponse(
        response: ActionMessageDTO,
        context: Context,
    ) {
        response.payload[response.action.name]?.let { mapUrl ->
            GoogleMapUtils.openGoogleMapUrl(
                context,
                mapUrl,
            )
        }
    }

    // from here
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
}
