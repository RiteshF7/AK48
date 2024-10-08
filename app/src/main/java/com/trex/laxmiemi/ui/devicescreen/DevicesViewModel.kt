package com.trex.laxmiemi.ui.devicescreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trex.laxmiemi.data.firebase.firestore.DeviceFirestore
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexcommon.data.NewDevice

class DevicesViewModel : ViewModel() {
    val _devices = MutableLiveData<List<NewDevice>>()
    val devices: LiveData<List<NewDevice>> = _devices
    private val devicesFirestore = DeviceFirestore(CommonConstants.shodId)

    fun getAllDevices() {
        devicesFirestore.getAllDevices({
            _devices.value = it
        }, {
            Log.i(TAG, "getAllDevices: error getting device!!")
        })
    }

    companion object {
        const val TAG = "DevicesViewModel"
    }
}
