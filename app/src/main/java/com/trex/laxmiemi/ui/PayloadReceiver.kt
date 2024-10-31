package com.trex.laxmiemi.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.trex.laxmiemi.handlers.ActionExecutor
import com.trex.rexnetwork.data.ActionMessageDTOMapper

class PayloadReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        ActionMessageDTOMapper.getPayloadString(intent)?.let { actionMessageDTOString ->
            Log.i("onPayloadReceive", "onReceive: payload :::")
            ActionMessageDTOMapper.fromJsonToDTO(actionMessageDTOString)?.let { actionMessageDTO ->
                ActionExecutor(context).execute(actionMessageDTO)
            }
        } ?: {
            Log.e("", "onReceive: no payload found in broadcast !!")
        }
    }
}
