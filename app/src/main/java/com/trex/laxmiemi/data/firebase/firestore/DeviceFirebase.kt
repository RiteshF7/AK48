package com.trex.laxmiemi.data.firebase.firestore

import java.io.Serializable

data class Device(
    val imei: String = "",
    val customerName: String = "",
    val fcmTokenId: String = "",
    val lockStatus: Boolean = false,
) : Serializable

class DeviceFirestore(
    shopId: String,
) : FirestoreBase<Device>("shops/$shopId/devices") {
    override fun dataClass(): Class<Device> = Device::class.java

    // Create or update a device
    fun createOrUpdateDevice(
        deviceId: String? = null,
        device: Device,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        addOrUpdateDocument(deviceId, device, onSuccess, onFailure)
    }

    // Update lock status of a device
    fun updateLockStatus(
        deviceId: String,
        lockStatus: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        updateSingleField(deviceId, "lockStatus", lockStatus, onSuccess, onFailure)
    }

    // Delete a device
    fun deleteDevice(
        deviceId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        deleteDocument(deviceId, onSuccess, onFailure)
    }

    fun getAllDevices(
        onSuccess: (List<Device>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        getAllDocuments(dataClass(), onSuccess, onFailure)
    }

    fun getSingleDevice(
        deviceId: String,
        onSuccess: (Device) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        getDocument(deviceId, dataClass(), onSuccess, onFailure)
    }
}
