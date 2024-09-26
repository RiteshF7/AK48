package com.trex.laxmiemi.ui.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeScreenViewmodel : ViewModel() {


    private val _dealerCode = MutableLiveData("11458")
    val dealerCode: LiveData<String> = _dealerCode

    private val _balanceKeys = MutableLiveData(3)
    val balanceKeys: LiveData<Int> = _balanceKeys

    // Add more LiveData fields as needed

    fun onLogout() {
        // Implement logout logic
    }

    // Add more functions for other button clicks


}