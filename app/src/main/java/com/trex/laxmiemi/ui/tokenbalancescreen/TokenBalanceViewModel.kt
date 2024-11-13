package com.trex.laxmiemi.ui.tokenbalancescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexnetwork.domain.firebasecore.firesstore.Shop
import com.trex.rexnetwork.domain.firebasecore.firesstore.ShopFirestore
import com.trex.rexnetwork.utils.SharedPreferenceManager

// UI State
data class UnlockUiState(
    val balance: String = "",
    val usedToken: String = "",
)

class TokenBalanceViewModel : ViewModel() {
    private val _uiState = mutableStateOf(UnlockUiState())
    val uiState: State<UnlockUiState> = _uiState

    private val shopRepo = ShopFirestore()
    private val db = SharedPreferenceManager(CommonConstants.applicationContext)

    init {
        getTotalToken()
//        getUsedToken()
    }

    fun getTotalToken() {
        db.getShopId()?.let { shopId ->
            shopRepo.getTokenBalanceList(shopId, { tokenList ->
                val count = (tokenList as List<String>).size.toString()
                _uiState.value =
                    _uiState.value.copy(
                        balance = count,
                    )
            })
        }
    }

    fun getUsedToken() {
        db.getShopId()?.let { shopId ->
            shopRepo.getSingleField(shopId, Shop::deletedDevices.name, { tokenList ->
                val count = (tokenList as List<String>).size.toString()
                _uiState.value =
                    _uiState.value.copy(
                        usedToken = count,
                    )
            }, {
            })
        }
    }
}
