package com.trex.laxmiemi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.trex.rexcommon.data.NotificationBody
import com.trex.rexcommon.data.RetrofitClient
import com.trex.rexcommon.data.SendMessageDto
import kotlinx.coroutines.launch

class MainActivityViewModel :ViewModel() {

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: LiveData<FirebaseUser?> get() = _firebaseUser
    val mAuth = Firebase.auth

    private fun isUserLoggedIn() = mAuth.currentUser

    init {
        _firebaseUser.postValue(isUserLoggedIn())
    }

    fun signOut(){
        mAuth.signOut()
        _firebaseUser.postValue(isUserLoggedIn())
    }

    fun sendMessage(){
        viewModelScope.launch {
            val data = SendMessageDto(
                "some token",
                NotificationBody("some title","some des")
            )

            try {
                RetrofitClient.builder.sendMessage(data)
            }
            catch (error:Exception){
                Log.i("sendMessageerror", "sendMessage: $error")
            }

        }
    }
}