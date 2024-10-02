package com.trex.rexcommon.data.repository

import com.trex.rexcommon.data.RetrofitClient
import com.trex.rexcommon.data.SendMessageDto

class SendDataRepository {
    private val retrofit = RetrofitClient.builder

    suspend fun sendDataToServer(data: SendMessageDto) {
        retrofit.sendMessage(data)
    }
}
