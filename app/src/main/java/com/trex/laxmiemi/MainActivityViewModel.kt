package com.trex.laxmiemi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trex.rexcommon.data.NotificationBody
import com.trex.rexcommon.data.RetrofitClient
import com.trex.rexcommon.data.SendMessageDto
import kotlinx.coroutines.launch

class MainActivityViewModel :ViewModel() {

    fun sendMessage(){
        viewModelScope.launch {
            val data = SendMessageDto(
                "some token",
                NotificationBody("some title","some des")
            )

            try {
                RetrofitClient.builder.sendMessage(data)
            }
            catch (error:Exception){
                Log.i("sendMessageerror", "sendMessage: $error")
            }

        }
    }
}