package com.trex.laxmiemi.utils

import android.content.Context
import androidx.compose.ui.unit.dp

object CommonConstants {
    lateinit var appName: String

    fun initialize(context: Context) {
        appName = context.getString(context.applicationInfo.labelRes)
    }

    var shodId = ""

    val MaxScreenWidth = 500.dp
    val SINGLE_DEVICE_DATA = "single_device_data"
}
