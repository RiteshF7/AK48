package com.trex.laxmiemi.firebase.firestore

data class Device(
    val deviceId: String = "",
    val imei: String = "",
    val customerName: String = "",
    val emiStatus: String = "",
    val lastPaymentDate: String = "",
    val dateTimeSold: String = "",
    val status: String = "",
    val tokenId: String = "",
    val emiDueDate: String = "",
    val lockStatus: String = "",
    val lastCommunication: Long = 0L
)

class DeviceFirestore(shopId: String) : FirestoreBase<Device>("shops/$shopId/devices") {

        override fun dataClass(): Class<Device> {
            return Device::class.java
        }

        // Create or update a device
        fun createOrUpdateDevice(deviceId: String? = null, device: Device, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
            addOrUpdateDocument(deviceId, device, onSuccess, onFailure)
        }

        // Update lock status of a device
        fun updateLockStatus(deviceId: String, lockStatus: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
            updateSingleField(deviceId, "lockStatus", lockStatus, onSuccess, onFailure)
        }

        // Delete a device
        fun deleteDevice(deviceId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
            deleteDocument(deviceId, onSuccess, onFailure)
        }
    }

