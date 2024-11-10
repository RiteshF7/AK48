package com.trex.laxmiemi.utils

import android.content.Context
import androidx.compose.ui.unit.dp

object CommonConstants {
    lateinit var appName: String
    lateinit var applicationContext: Context
    const val KEY_SHOP_ID = "SHOP_ID"

    fun initialize(context: Context) {
        applicationContext = context
        appName = context.getString(context.applicationInfo.labelRes)
    }

    const val EXAMPLE_VIDEO_URI = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    var shodId = "+919910000163"

    val MaxScreenWidth = 250.dp
    val SINGLE_DEVICE_DATA = "single_device_data"
}
