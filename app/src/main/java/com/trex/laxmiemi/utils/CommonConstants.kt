package com.trex.laxmiemi.utils

import android.content.Context
import androidx.compose.ui.unit.dp

object CommonConstants {
    lateinit var appName: String
    lateinit var packageName: String
    const val KEY_SHOP_ID = "SHOP_ID"
    fun initialize(context: Context) {
        appName = context.getString(context.applicationInfo.labelRes)
        packageName = context.packageName
    }

    var shodId = "+919910000163"

    val MaxScreenWidth = 500.dp
    val SINGLE_DEVICE_DATA = "single_device_data"
}
