package com.trex.laxmiemi.ui.devicescreen

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.trex.rexnetwork.utils.getExtraData
import com.trex.rexnetwork.utils.startMyActivity
import kotlinx.parcelize.Parcelize

class DevicesActivity : ComponentActivity() {
    private lateinit var extraData: DevicesExtraData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extraData = intent.getExtraData<DevicesExtraData>()
        val vm: DevicesViewModel by viewModels()
        if (extraData.devicesType == DeviceScreenType.DELAYED) {
            vm.loadDevices(true)
        } else {
            vm.loadDevices(false)
        }

        setContent {
            DevicesScreen(vm)
        }
    }

    companion object {
        fun go(
            context: Context,
            devicesExtraData: DevicesExtraData = DevicesExtraData(),
        ) {
            context.startMyActivity(DevicesActivity::class.java, devicesExtraData)
        }
    }

    @Parcelize
    data class DevicesExtraData(
        val devicesType: DeviceScreenType = DeviceScreenType.ACTIVE,
    ) : Parcelable

    enum class DeviceScreenType {
        ACTIVE,
        INACTIVE,
        DELAYED,
    }
}
