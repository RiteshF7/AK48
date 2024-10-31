package com.trex.laxmiemi.handlers

import android.content.Context
import com.google.gson.Gson
import com.trex.laxmiemi.ui.createdevicescreen.CreateDeviceActivity
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.Actions
import com.trex.rexnetwork.data.DeviceInfo
import com.trex.rexnetwork.utils.startMyActivity

class HandleDeviceRegistration(
    private val context: Context,
) : BaseHandler() {
    override fun handle(messageDTO: ActionMessageDTO) {

    }
}
