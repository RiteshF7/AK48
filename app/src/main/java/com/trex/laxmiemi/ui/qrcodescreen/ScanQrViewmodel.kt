package com.trex.laxmiemi.ui.qrcodescreen

import NewDeviceIds
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trex.laxmiemi.utils.QrUtils
import com.trex.rexnetwork.domain.firebasecore.firesstore.DataFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class ScanQrUiState {
    object Loading : ScanQrUiState()

    data class Success(
        val bitmap: Bitmap,
    ) : ScanQrUiState()

    data class Failed(
        val exception: Exception,
    ) : ScanQrUiState()
}

class ScanQrViewmodel : ViewModel() {
    private val _scanQrUiState = mutableStateOf<ScanQrUiState>(ScanQrUiState.Loading)
    val scanQrUiState: State<ScanQrUiState> = _scanQrUiState
    private val dataFirestore = DataFirestore()

    fun getQrBitMap(deviceIds:NewDeviceIds) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dataFirestore.getExtraData({ extraData ->
                    val bitmap = QrUtils(extraData).getQrBitmap(deviceIds)
                    _scanQrUiState.value = ScanQrUiState.Success(bitmap)
                }, { error ->
                    _scanQrUiState.value = ScanQrUiState.Failed(error)
                })
            }
        }
    }
}
