package com.trex.rexcommon.data.repository

import android.util.Log
import com.trex.rexcommon.data.RetrofitClient
import com.trex.rexcommon.data.SendMessageDto

class SendDataRepository {
    private val retrofit = RetrofitClient.builder

    suspend fun sendDataToServer(data: SendMessageDto) {
        try {
            retrofit.sendMessage(data)
        }
        catch (error: Exception){
            Log.i("errorrr", "sendDataToServer: ${error}")
        }

    }
}
