package com.trex.laxmiemi.ui.devicescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.laxmiemi.utils.EMIUtility
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeletedDeviceFirebase
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeviceFirestore

// ViewStates to represent different UI states
sealed class DevicesViewState {
    object Loading : DevicesViewState()

    data class Success(
        val devices: List<DeviceWithEMIStatus>,
    ) : DevicesViewState()

    data class Error(
        val message: String,
    ) : DevicesViewState()
}

// Data classes to hold device and EMI information
data class DeviceWithEMIStatus(
    val device: NewDevice,
    val isDeviceLocked: Boolean,
    val emiStatus: EMIStatus,
)

data class EMIStatus(
    val isDelayed: Boolean,
    val delayInDays: Int,
    val isCompleted: Boolean,
    val nextDueDate: String,
    val remainingEMIs: Int,
)

class DevicesViewModel : ViewModel() {
    private val _viewState = MutableLiveData<DevicesViewState>()
    val viewState: LiveData<DevicesViewState> = _viewState
    private var isDelayedDevices = false

    private val devicesFirestore = DeviceFirestore(CommonConstants.shodId)
    private val deletedDeviceFirebase = DeletedDeviceFirebase(CommonConstants.shodId)

    fun loadDevices(isDelayed: Boolean) {
        isDelayedDevices = isDelayed
        _viewState.value = DevicesViewState.Loading
        devicesFirestore.getAllDevices(
            onSuccess = { devices ->
                processDevices(devices, isDelayed)
            },
            onFailure = { _viewState.value = DevicesViewState.Error("Failed to fetch devices") },
        )
    }

    private fun processDevices(
        devices: List<NewDevice>,
        isDelayed: Boolean,
    ) {
        try {
            val processedDevices =
                devices.map { device ->
                    processDeviceEMIStatus(device)
                }
            if (isDelayed) {
                val delayedDevices = processedDevices.filter { it.emiStatus.isDelayed }
                _viewState.value = DevicesViewState.Success(delayedDevices)
            } else {
                _viewState.value = DevicesViewState.Success(processedDevices)
            }
        } catch (e: Exception) {
            _viewState.value = DevicesViewState.Error("Error processing devices: ${e.message}")
        }
    }

    private fun processDeviceEMIStatus(device: NewDevice): DeviceWithEMIStatus {
        val emiUtility =
            EMIUtility(
                firstDueDate = device.firstDueDate,
                durationInMonths = device.durationInMonths.toIntOrNull() ?: 0,
            )

        val status = emiUtility.getEMIStatus(device.dueDate)

        return DeviceWithEMIStatus(
            device = device,
            isDeviceLocked = device.deviceLockStatus,
            emiStatus =
                EMIStatus(
                    isDelayed = status.isDelayed,
                    delayInDays = status.delayedDays.toInt(),
                    isCompleted = status.isCompleted,
                    nextDueDate = emiUtility.getNextMonthDate(device.dueDate),
                    remainingEMIs = status.remainingEMIs,
                ),
        )
    }

    fun markEmiAsPaid(deviceWithEMIStatus: DeviceWithEMIStatus) {
        val device = deviceWithEMIStatus.device
        val nextDueDate = deviceWithEMIStatus.emiStatus.nextDueDate
        devicesFirestore.updateDueDate(
            device.deviceId,
            nextDueDate,
            onSuccess = { loadDevices(isDelayedDevices) },
            onFailure = { /* Handle error */ },
        )
    }

    fun deleteDevice(device: NewDevice) {
        devicesFirestore.deleteDevice(
            device.deviceId,
            onSuccess = {
                deletedDeviceFirebase.createOrUpdateDevice(
                    device.deviceId,
                    device,
                    onSuccess = { loadDevices(isDelayedDevices) },
                    onFailure = { /* Handle error */ },
                )
            },
            onFailure = { /* Handle error */ },
        )
    }
}
