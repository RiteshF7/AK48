package com.trex.rexandroidsecureclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivityViewModel : ViewModel() {
    private val _currentToken = MutableLiveData("")
    val currentToken: LiveData<String> = _currentToken;

    fun getCurrentToken() {
        viewModelScope.launch {
            val localToken = Firebase.messaging.token.await()
            _currentToken.value = localToken
        }
    }
}
