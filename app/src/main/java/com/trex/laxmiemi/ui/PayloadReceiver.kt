package com.trex.laxmiemi.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.trex.laxmiemi.ui.actionresultscreen.ActionResultActivity
import com.trex.rexnetwork.data.ActionMessageDTOMapper

class PayloadReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        ActionMessageDTOMapper.getPayloadString(intent)?.let { actionMessageDTO ->
            val intent =
                Intent(context, ActionResultActivity::class.java).apply {
                    putExtra(ActionResultActivity.ACTION_RESULT_INTENT_KEY, actionMessageDTO)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            context.startActivity(intent)

            // show some dialog ui for payload
//            val actionExecuter = ActionExecuter(context)
//            actionExecuter.execute(actionMessageDTO.action, actionMessageDTO.payload)
        } ?: {
            Log.e("", "onReceive: no payload found in broadcast !!")
        }
    }
}
