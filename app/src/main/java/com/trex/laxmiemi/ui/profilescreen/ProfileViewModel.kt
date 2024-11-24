package com.trex.laxmiemi.ui.profilescreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeletedDeviceFirebase
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeviceFirestore
import com.trex.rexnetwork.domain.firebasecore.firesstore.ShopFirestore

data class ProfileState(
    val shopId: String = "",
    val shopName: String = "",
    val tokenBalance: String = "",
    val activeDevice: String = "",
    val deactivatedDevices: String = "",
)

class ProfileViewModel : ViewModel() {
    private val shopFirebase = ShopFirestore()
    private val deviceFirebase = DeviceFirestore(CommonConstants.shodId)
    private val deletedDeviceFirebase = DeletedDeviceFirebase(CommonConstants.shodId)
    private val _profileUiState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileUiState

    init {
        getShopById()
    }

    fun getShopById() {
        shopFirebase.getShopById(CommonConstants.shodId, { shop ->
            val tokenBalance =
                if (shop.tokenBalance.isNullOrEmpty()) "0" else shop.tokenBalance.size.toString()
            _profileUiState.value =
                _profileUiState.value.copy(
                    shopId = shop.shopCode,
                    shopName = shop.shopName,
                    tokenBalance = tokenBalance,
                )
            updateActiveDevicesCount()
        }, {
            Log.e("", "getShopById error: ${it.message}")
        })
    }

    private fun updateActiveDevicesCount() {
        deviceFirebase.getAllDevices({ list ->
            if (list.isNullOrEmpty()) {
                _profileUiState.value =
                    _profileUiState.value.copy(
                        activeDevice = "0",
                    )
            } else {
                _profileUiState.value =
                    _profileUiState.value.copy(
                        activeDevice = list.size.toString(),
                    )
            }
            updateDeactivedDevicesCount()
        }, {
            Log.e("", "getShopById error: ${it.message}")
        })
    }

    private fun updateDeactivedDevicesCount() {
        deletedDeviceFirebase.getAllDevices({ list ->
            if (list.isEmpty()) {
                _profileUiState.value =
                    _profileUiState.value.copy(
                        activeDevice = "0",
                    )
            } else {
                _profileUiState.value =
                    _profileUiState.value.copy(
                        deactivatedDevices = list.size.toString(),
                    )
            }
        }, {
            Log.e("", "getShopById error: ${it.message}")
        })
    }
}
