package com.trex.rexcommon.data

data class NewDevice(
    val shopId: String = "dump_shop",
    val imeiOne: Int,
    val imeiTwo: Int = 0,
    val fcmToken: String,
)
