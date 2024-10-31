package com.trex.laxmiemi.handlers

import android.content.Context
import com.google.gson.Gson
import com.trex.laxmiemi.ui.createdevicescreen.CreateDeviceActivity
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.Actions
import com.trex.rexnetwork.data.DeviceInfo
import com.trex.rexnetwork.utils.SharedPreferenceManager
import com.trex.rexnetwork.utils.startMyActivity

class HandleDeviceRegistration(
    private val context: Context,
) : BaseHandler() {
    val sharedPreferences = SharedPreferenceManager(CommonConstants.applicationContext)
    val shopId = sharedPreferences.getShopId() ?: ""

    override fun handle(messageDTO: ActionMessageDTO) {
        val deviceInfoPayload = messageDTO.payload[Actions.ACTION_REG_DEVICE.name]
        val deviceInfo = Gson().fromJson(deviceInfoPayload, DeviceInfo::class.java)
        context.startMyActivity(CreateDeviceActivity::class.java, deviceInfo)
    }
}
