package com.trex.laxmiemi.handlers

import android.content.Context
import com.google.gson.Gson
import com.trex.laxmiemi.ui.createdevicescreen.CreateDeviceActivity
import com.trex.laxmiemi.ui.createdevicescreen.FormData
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.DeviceInfo
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.utils.SharedPreferenceManager

class HandleDeviceRegistration(
    private val context: Context,
) : BaseHandler() {
    val sharedPreferences = SharedPreferenceManager(CommonConstants.applicationContext)
    val shopId = sharedPreferences.getShopId() ?: ""

    override fun handle(messageDTO: ActionMessageDTO) {
        val newDeviceString = messageDTO.payload[messageDTO.action.name]
        val deviceInfo = Gson().fromJson(newDeviceString, DeviceInfo::class.java)
        val formData = FormData(deviceModel = "${deviceInfo.manufacturer} ${deviceInfo.brand}")
        CreateDeviceActivity.startCreateDeviceActivity(context, formData)
    }

    fun createNewDevice(deviceInfo: DeviceInfo): NewDevice {
        val newDevice =
            NewDevice(
                shopId,
                deviceInfo.fcmToken,
                imeiOne = deviceInfo.imeiOne,
                manufacturer = deviceInfo.manufacturer,
                brand = deviceInfo.brand,
                modelNumber = deviceInfo.modelNumber,
                androidVersion = deviceInfo.androidVersion,
                imeiTwo = deviceInfo.imeiTwo,
                deviceCode = deviceInfo.deviceCode,
            )

        return newDevice
    }
}
