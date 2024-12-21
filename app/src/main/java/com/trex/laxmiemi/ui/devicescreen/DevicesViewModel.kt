package com.trex.laxmiemi.ui.devicescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeletedDeviceFirebase
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeviceFirestore

// ViewStates to represent different UI states
sealed class DevicesViewState {
    object Loading : DevicesViewState()

    data class Success(
        val devices: List<NewDevice>,
    ) : DevicesViewState()

    data class Error(
        val message: String,
    ) : DevicesViewState()

    data class DeviceRegIncomplete(
        val device: NewDevice,
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
    private var allDevicesBackup = listOf<NewDevice>()

    private val devicesFirestore = DeviceFirestore(CommonConstants.shodId)
    private val deletedDeviceFirebase = DeletedDeviceFirebase(CommonConstants.shodId)

    fun loadDevices(isDelayed: Boolean) {
        isDelayedDevices = isDelayed
        _viewState.value = DevicesViewState.Loading
        devicesFirestore.getAllDevices(
            onSuccess = { devices ->
                allDevicesBackup = devices
                _viewState.value =
                    DevicesViewState.Success(devices.sortedByDescending { it.createdAt.seconds })
//                processDevices(devices, isDelayed)
            },
            onFailure = { _viewState.value = DevicesViewState.Error("Failed to fetch devices") },
        )
    }

//    private fun processDevices(
//        devices: List<NewDevice>,
//        isDelayed: Boolean,
//    ) {
//        try {
//            val processedDevices =
//                devices.map { device ->
//                    if (device.dueDate.isEmpty() || device.firstDueDate.isEmpty()) {
//                        _viewState.value = DevicesViewState.DeviceRegIncomplete(device)
//                        return
//                    }
//                    processDeviceEMIStatus(device)
//                }
//
//            if (isDelayed) {
//                val delayedDevices = processedDevices.filter { it.emiStatus.isDelayed == true }
//
//                // Debug log before sorting
//                Log.i("processDevices", "Before sorting (delayedDevices):")
//                delayedDevices.forEach {
//                    Log.i(
//                        "processDevices",
//                        it.device.createdAt
//                            .toDate()
//                            .toString(),
//                    )
//                }
//
//                val sortedDelayedDevices =
//                    delayedDevices.sortedByDescending { it.device.createdAt.seconds }
//
//                // Debug log after sorting
//                Log.i("processDevices", "After sorting (delayedDevices):")
//                sortedDelayedDevices.forEach {
//                    Log.i(
//                        "processDevices",
//                        it.device.createdAt
//                            .toDate()
//                            .toString(),
//                    )
//                }
//                allDevicesBackup = sortedDelayedDevices
//                _viewState.value = DevicesViewState.Success(sortedDelayedDevices)
//            } else {
//                // Debug log before sorting
//                Log.i("processDevices", "Before sorting (processedDevices):")
//                processedDevices.forEach {
//                    Log.i(
//                        "processDevices",
//                        it.device.createdAt
//                            .toDate()
//                            .toString(),
//                    )
//                }
//
//                val sortedProcessedDevices =
//                    processedDevices.sortedByDescending { it.device.createdAt.seconds }
//
//                // Debug log after sorting
//                Log.i("processDevices", "After sorting (processedDevices):")
//                sortedProcessedDevices.forEach {
//                    Log.i(
//                        "processDevices",
//                        it.device.createdAt
//                            .toDate()
//                            .toString(),
//                    )
//                }
//                allDevicesBackup = sortedProcessedDevices
//                _viewState.value = DevicesViewState.Success(sortedProcessedDevices)
//            }
//        } catch (e: Exception) {
//            _viewState.value = DevicesViewState.Error("Error processing devices: ${e.message}")
//        }
//    }
//
//    private fun processDeviceEMIStatus(device: NewDevice): DeviceWithEMIStatus {
//        val emiUtility =
//            EMIUtility(
//                firstDueDate = device.firstDueDate,
//                durationInMonths = device.durationInMonths.toIntOrNull() ?: 0,
//            )
//
//        val status = emiUtility.getEMIStatus(device.dueDate)
//
//        return DeviceWithEMIStatus(
//            device = device,
//            isDeviceLocked = device.deviceLockStatus,
//            emiStatus =
//                EMIStatus(
//                    isDelayed = status.isDelayed,
//                    delayInDays = status.delayedDays.toInt(),
//                    isCompleted = status.isCompleted,
//                    nextDueDate = emiUtility.getNextMonthDate(device.dueDate),
//                    remainingEMIs = status.remainingEMIs,
//                ),
//        )
//    }
//
//    fun markEmiAsPaid(deviceWithEMIStatus: DeviceWithEMIStatus) {
//        val device = deviceWithEMIStatus.device
//        val nextDueDate = deviceWithEMIStatus.emiStatus.nextDueDate
//        devicesFirestore.updateDueDate(
//            device.deviceId,
//            nextDueDate,
//            onSuccess = { loadDevices(isDelayedDevices) },
//            onFailure = { /* Handle error */ },
//        )
//    }

    fun searchDevice(query: String) {
        if (_viewState.value is DevicesViewState.Success) {
            if (query.isEmpty()) {
                _viewState.value = DevicesViewState.Success(allDevicesBackup)
                return
            }
            val allDevices = (_viewState.value as DevicesViewState.Success).devices ?: emptyList()
            val filteredDevices =
                allDevices.filter { deviceWithStatus ->
                    val device = deviceWithStatus
                    device.costumerName.contains(query, ignoreCase = true) ||
                        device.costumerPhone.contains(query, ignoreCase = true)
                    device.email.contains(query, ignoreCase = true)
                }
            _viewState.value = DevicesViewState.Success(filteredDevices)
        }
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
