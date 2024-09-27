package com.trex.laxmiemi.ui.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trex.laxmiemi.firebase.firestore.Shop
import com.trex.laxmiemi.firebase.firestore.ShopFirestore

class HomeScreenViewmodel : ViewModel() {


    private val _dealerCode = MutableLiveData("------")
    val dealerCode: LiveData<String> = _dealerCode
    val shopFirestore = ShopFirestore();

    init {
        getDealerCode()
    }

    private val _balanceKeys = MutableLiveData(3)
    val balanceKeys: LiveData<Int> = _balanceKeys

    private fun getDealerCode() {
        return shopFirestore.getSingleField("shop1", Shop::dealerCode.name,
            onSuccess = {
                    _dealerCode.value = it.toString()
        }, onFailure = {
            _dealerCode.value = "------"
        })
    }

    // Add more LiveData fields as needed

    fun onLogout() {
        // Implement logout logic
    }

    // Add more functions for other button clicks


}