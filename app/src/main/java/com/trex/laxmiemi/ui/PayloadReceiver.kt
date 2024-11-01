package com.trex.laxmiemi.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.trex.laxmiemi.handlers.ShopActionExecutor
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.ActionMessageDTOMapper
import com.trex.rexnetwork.domain.firebasecore.fcm.fcmrequestscreen.FcmResponseManager

class PayloadReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        Log.d("PayloadReceiver", "onReceive: Received broadcast!")
        val actionMessageDTO = extractActionMessageDTO(intent)

        if (actionMessageDTO != null) {
            handleAction(context, actionMessageDTO)
        } else {
            Log.e("PayloadReceiver", "onReceive: No payload found in broadcast!")
        }
    }

    private fun handleAction(
        context: Context,
        actionMessageDTO: ActionMessageDTO,
    ) {
        val shopActionExec = ShopActionExecutor(context)

        if (FcmResponseManager.hasRequest(actionMessageDTO.requestId)) {
            shopActionExec.receiveResponse(actionMessageDTO)
            return
        }

        shopActionExec.receiveAction(actionMessageDTO)

//        if (actionMessageDTO.action.hasReaction()) {
//            FcmResponseManager.handleResponse(actionMessageDTO.requestId, actionMessageDTO)
//        } else {
//            ShopActionExecutor(context).execute(actionMessageDTO)
//        }
    }

    private fun extractActionMessageDTO(intent: Intent): ActionMessageDTO? {
        val payloadString = ActionMessageDTOMapper.getPayloadString(intent)
        if (payloadString == null) {
            Log.w("PayloadReceiver", "extractActionMessageDTO: Payload string is null")
            return null
        }
        return ActionMessageDTOMapper.fromJsonToDTO(payloadString)
    }
}
