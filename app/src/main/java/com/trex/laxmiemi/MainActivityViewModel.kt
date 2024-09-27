package com.trex.laxmiemi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.trex.laxmiemi.firebase.firestore.FireStoreExeptions
import com.trex.laxmiemi.firebase.firestore.Shop
import com.trex.laxmiemi.firebase.firestore.ShopFirestore
import com.trex.laxmiemi.utils.CommonConstants


class MainActivityViewModel : ViewModel() {
    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: LiveData<FirebaseUser?> get() = _firebaseUser
    private val mAuth = Firebase.auth
    private val _dealerCode = MutableLiveData("------")
    val dealerCode: LiveData<String> = _dealerCode
    private val shopFirestore = ShopFirestore();

    init {
        _firebaseUser.postValue(getCurrentUser())
    }

    private fun getDealerCode() {
        shopFirestore.getSingleField(CommonConstants.shodId, Shop::dealerCode.name,
            onSuccess = {
                _dealerCode.value = it.toString()
            }, onFailure = {
                _dealerCode.value = "------"
            })

    }

    private fun getCurrentUser() = mAuth.currentUser


    fun signOut() {
        mAuth.signOut()
        _firebaseUser.postValue(getCurrentUser())
    }

    fun checkIfShopExists() {
        val userPhoneNumber = mAuth.currentUser?.phoneNumber
        if (userPhoneNumber.isNullOrEmpty()) return

        shopFirestore.getShopById(userPhoneNumber.toString(),
            {
                CommonConstants.shodId = userPhoneNumber;
                getDealerCode()
            },
            { error ->
                if (error.message == FireStoreExeptions.DOC_NOT_FOUND.toString()) {
                    createNewShop(userPhoneNumber);
                }
            })
    }

    private fun createNewShop(userPhoneNumber: String) {
        shopFirestore.createOrUpdateShop(
            userPhoneNumber,
            Shop(dealerCode = kotlin.random.Random.nextInt(1, 100000).toString()),
            {
                Log.i("", "createNewShop: ShopCreatedSuccessfully !")
                CommonConstants.shodId = userPhoneNumber;
            },
            {
                Log.i("TAG", "error createNewShop: ")
            })
    }

}