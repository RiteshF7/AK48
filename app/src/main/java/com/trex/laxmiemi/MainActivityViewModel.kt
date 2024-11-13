package com.trex.laxmiemi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexnetwork.domain.firebasecore.fcm.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.fcm.ShopFcmTokenUpdater
import com.trex.rexnetwork.domain.firebasecore.firesstore.FCMTokenFirestore
import com.trex.rexnetwork.domain.firebasecore.firesstore.FireStoreExeptions
import com.trex.rexnetwork.domain.firebasecore.firesstore.Shop
import com.trex.rexnetwork.domain.firebasecore.firesstore.ShopFirestore
import com.trex.rexnetwork.utils.SharedPreferenceManager

class MainActivityViewModel : ViewModel() {
    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: LiveData<FirebaseUser?> get() = _firebaseUser
    private val mAuth = Firebase.auth
    private val _dealerCode = MutableLiveData("------")
    val dealerCode: LiveData<String> = _dealerCode
    private val shopFirestore = ShopFirestore()
    private val fcmTokenFirestore = FCMTokenFirestore()

    init {
        _firebaseUser.postValue(getCurrentUser())
    }

    private fun getDealerCode() {
        shopFirestore.getSingleField(
            CommonConstants.shodId,
            Shop::shopCode.name,
            onSuccess = {
                _dealerCode.value = it.toString()
            },
            onFailure = {
                _dealerCode.value = "------"
            },
        )
    }

    private fun getCurrentUser() = mAuth.currentUser

    fun signOut() {
        mAuth.signOut()
        _firebaseUser.postValue(getCurrentUser())
    }

    fun checkIfShopExists(
        fcmTokenManager: FCMTokenManager,
        mshardPref: SharedPreferenceManager,
        updater: ShopFcmTokenUpdater,
        onComplete: () -> Unit,
    ) {
        val userPhoneNumber = mAuth.currentUser?.phoneNumber
        if (userPhoneNumber.isNullOrEmpty()) return

        shopFirestore.getShopById(
            userPhoneNumber.toString(),
            { shop ->
                mshardPref.saveShopId(userPhoneNumber)
                fcmTokenFirestore.getFcmToken(userPhoneNumber) {
                    fcmTokenManager.refreshToken(it)
                }
                onComplete()
                getDealerCode()
            },
            { error ->
                if (error.message == FireStoreExeptions.DOC_NOT_FOUND.toString()) {
                    createNewShop(userPhoneNumber, fcmTokenManager, mshardPref) {
                        onComplete()
                    }
                }
            },
        )
    }

    private fun createNewShop(
        userPhoneNumber: String,
        fcmTokenManager: FCMTokenManager,
        mshardPref: SharedPreferenceManager,
        onComplete: () -> Unit,
    ) {
        // Todo make shop screen
        shopFirestore.createOrUpdateShop(
            userPhoneNumber,
            Shop(),
            {
                Log.i("", "createNewShop: ShopCreatedSuccessfully !")
                mshardPref.saveShopId(userPhoneNumber)
                fcmTokenManager.refreshToken("")
                onComplete()
            },
            {
                Log.i("TAG", "error createNewShop: ")
            },
        )
    }
}
