package com.trex.laxmiemi.ui.devicedetailsscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trex.laxmiemi.data.firebase.firestore.Device
import com.trex.laxmiemi.data.firebase.firestore.DeviceFirestore
import com.trex.laxmiemi.ui.devicescreen.DevicesViewModel.Companion.TAG
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexcommon.data.SendMessageDto
import com.trex.rexcommon.data.repository.SendDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeviceScreenDetailViewModel : ViewModel() {
    private val repo = SendDataRepository()

    private val _devices = MutableLiveData<Device>()
    val devices: LiveData<Device> = _devices
    private val devicesFirestore = DeviceFirestore(CommonConstants.shodId)

    fun getAllDevices() {
        devicesFirestore.getSingleDevice("123456789",{
            _devices.value = it
        }, {
            Log.i(TAG, "getAllDevices: error getting device!!")
        })
    }

    fun sendAction(data: SendMessageDto) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.sendDataToServer(data)
            }
        }
    }
}
