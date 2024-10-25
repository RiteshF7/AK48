package com.trex.laxmiemi.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.trex.rexnetwork.data.ActionMessageDTOMapper

class PayloadReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {

        ActionMessageDTOMapper.getMessageDTOFromIntent(intent)?.let { actionMessageDTO ->
            Log.i("", "onReceive: ${actionMessageDTO.action}")
            //show some dialog ui for payload
//            val actionExecuter = ActionExecuter(context)
//            actionExecuter.execute(actionMessageDTO.action, actionMessageDTO.payload)
        }
    }
}
