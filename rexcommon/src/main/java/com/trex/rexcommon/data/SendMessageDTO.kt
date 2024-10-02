package com.trex.rexcommon.data

enum class DeviceActions {
    LOCK_DEVICE,
    UNLOCK_DEVICE,
}

data class SendMessageDto(
    val to: String?,
    val action: DeviceActions,
    val payload: Map<String, String> = emptyMap(),
)
