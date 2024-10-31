package com.trex.laxmiemi.handlers

import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.trex.laxmiemi.ui.actionresultscreen.ActionResultActivity
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.Actions
import com.trex.rexnetwork.domain.firebasecore.fcm.fcmrequestscreen.FcmResponseManager

class ActionExecutor(
    private val context: Context,
) {
    fun execute(messageDTO: ActionMessageDTO) {

        FcmResponseManager.handleResponse(messageDTO.requestId,messageDTO)
        val action = messageDTO.action
        when {
            action == Actions.ACTION_REG_DEVICE -> {
                HandleDeviceRegistration(context).handle(messageDTO)
            }

            else -> {
                startResultActivity(context, messageDTO)
            }
        }
    }

    fun startResultActivity(
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
