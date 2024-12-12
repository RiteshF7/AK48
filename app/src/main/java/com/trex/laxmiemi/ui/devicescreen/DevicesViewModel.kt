package com.trex.laxmiemi.ui.devicescreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.laxmiemi.utils.EMIUtility
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeletedDeviceFirebase
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeviceFirestore

enum class DeviceScreenType {
    ACTIVE,
    DELETED,
    DELAYED,
}

sealed class DevicesViewState {
    object Loading : DevicesViewState()

    data class Success(
        val devices: List<DeviceWithEMIStatus>,
    ) : DevicesViewState()

    data class Error(
        val message: String,
    ) : DevicesViewState()
}

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

    private val devicesFirestore = DeviceFirestore(CommonConstants.shodId)
    private val deletedDeviceFirebase = DeletedDeviceFirebase(CommonConstants.shodId)

    fun loadData(extraData: DevicesActivity.DevicesExtraData) {
        _viewState.value = DevicesViewState.Loading
        when (extraData.devicesType) {
            DeviceScreenType.ACTIVE -> fetchActiveDevices()
            DeviceScreenType.DELETED -> fetchDeletedDevices()
            DeviceScreenType.DELAYED -> fetchDelayedDevices()
        }
    }

    private fun fetchActiveDevices() {
        devicesFirestore.getAllDevices(
            onSuccess = { devices ->
                processDevices(devices)
            },
            onFailure = {
                _viewState.value = DevicesViewState.Error("Failed to fetch active devices")
                Log.e(TAG, "Error getting active devices", it)
            },
        )
    }

    private fun fetchDeletedDevices() {
        deletedDeviceFirebase.getAllDevices(
            onSuccess = { devices ->
                processDevices(devices)
            },
            onFailure = {
                _viewState.value = DevicesViewState.Error("Failed to fetch deleted devices")
                Log.e(TAG, "Error getting deleted devices", it)
            },
        )
    }

    private fun fetchDelayedDevices() {
        devicesFirestore.getAllDevices(
            onSuccess = { allDevices ->
                val delayedDevices =
                    allDevices.filter { device ->
                        processDeviceEMIStatus(device).emiStatus.isDelayed
                    }
                _viewState.value =
                    DevicesViewState.Success(delayedDevices.map { processDeviceEMIStatus(it) })
            },
            onFailure = {
                _viewState.value = DevicesViewState.Error("Failed to fetch delayed devices")
                Log.e(TAG, "Error getting delayed devices", it)
            },
        )
    }

    private fun processDevices(devices: List<NewDevice>) {
        try {
            val processedDevices =
                devices.map { device ->
                    processDeviceEMIStatus(device)
                }
            _viewState.value = DevicesViewState.Success(processedDevices)
        } catch (e: Exception) {
            _viewState.value = DevicesViewState.Error("Error processing devices: ${e.message}")
            Log.e(TAG, "Error processing devices", e)
        }
    }

    private fun processDeviceEMIStatus(device: NewDevice): DeviceWithEMIStatus =
        try {
            val emiUtility =
                EMIUtility(
                    firstDueDate = device.firstDueDate,
                    durationInMonths = device.durationInMonths.toIntOrNull() ?: 0,
                    initialCurrentDueDate = device.dueDate,
                ) { updatedDueDate ->
                    // Handle due date updates if needed
                    Log.d(TAG, "Due date updated for device ${device.deviceId}: $updatedDueDate")
                }

            val utilStatus = emiUtility.getEMIStatus()

            DeviceWithEMIStatus(
                device = device,
                isDeviceLocked = device.isDeviceLocked,
                emiStatus =
                    EMIStatus(
                        isDelayed = utilStatus.isDelayed,
                        delayInDays = utilStatus.delayedDays.toInt(),
                        isCompleted = utilStatus.isCompleted,
                        nextDueDate = utilStatus.nextDueDate,
                        remainingEMIs = utilStatus.remainingEMIs,
                    ),
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error processing EMI status for device ${device.deviceId}", e)
            // Return a default status in case of error
            DeviceWithEMIStatus(
                device = device,
                isDeviceLocked = device.isDeviceLocked,
                emiStatus =
                    EMIStatus(
                        isDelayed = false,
                        delayInDays = 0,
                        isCompleted = false,
                        nextDueDate = device.dueDate,
                        remainingEMIs = 0,
                    ),
            )
        }

    companion object {
        private const val TAG = "DevicesViewModel"
    }
}
