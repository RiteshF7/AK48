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
import com.trex.rexnetwork.domain.firebasecore.firesstore.FireStoreExeptions
import com.trex.rexnetwork.domain.firebasecore.firesstore.Shop
import com.trex.rexnetwork.domain.firebasecore.firesstore.ShopFirestore
import kotlin.random.Random

class MainActivityViewModel : ViewModel() {
    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: LiveData<FirebaseUser?> get() = _firebaseUser
    private val mAuth = Firebase.auth
    private val _dealerCode = MutableLiveData("------")
    val dealerCode: LiveData<String> = _dealerCode
    private val shopFirestore = ShopFirestore()

    init {
        _firebaseUser.postValue(getCurrentUser())
    }

    private fun getDealerCode() {
        shopFirestore.getSingleField(
            CommonConstants.shodId,
            Shop::dealerCode.name,
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
        onComplete: () -> Unit,
    ) {
        val userPhoneNumber = mAuth.currentUser?.phoneNumber
        if (userPhoneNumber.isNullOrEmpty()) return

        shopFirestore.getShopById(
            userPhoneNumber.toString(),
            { shop ->
                fcmTokenManager.refreshToken(shop.fcmToken)
                CommonConstants.shodId = userPhoneNumber
                onComplete()
                getDealerCode()
            },
            { error ->
                if (error.message == FireStoreExeptions.DOC_NOT_FOUND.toString()) {
                    createNewShop(userPhoneNumber, fcmTokenManager) {
                        onComplete()
                    }
                }
            },
        )
    }

    private fun createNewShop(
        userPhoneNumber: String,
        fcmTokenManager: FCMTokenManager,
        onComplete: () -> Unit,
    ) {
        val fcmToken = fcmTokenManager.getFcmToken() ?: "nope!"
        shopFirestore.createOrUpdateShop(
            userPhoneNumber,
            Shop(dealerCode = Random.nextInt(1, 100000).toString(), fcmToken = fcmToken),
            {
                Log.i("", "createNewShop: ShopCreatedSuccessfully !")
                CommonConstants.shodId = userPhoneNumber
                onComplete()
            },
            {
                Log.i("TAG", "error createNewShop: ")
            },
        )
    }
}
