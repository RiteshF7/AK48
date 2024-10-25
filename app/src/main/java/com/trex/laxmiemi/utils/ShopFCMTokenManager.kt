package com.trex.laxmiemi.utils

import android.content.Context
import android.util.Log
import com.trex.rexnetwork.domain.firebasecore.FCMTokenManager
import com.trex.rexnetwork.domain.firebasecore.Shop
import com.trex.rexnetwork.domain.firebasecore.ShopFirestore

class ShopFCMTokenManager(
    context: Context,
) : FCMTokenManager(context) {
    private val shopFirestore = ShopFirestore()
    private val updateField = Shop::shopFcmToken.name

    init {
        updateIfTokenOutdated { fcmToken ->
            updateOnFirestore(fcmToken)
        }
    }

    private fun updateOnFirestore(fcmToken: String) {
        mShardPref.getShopId()?.let { shopID ->
            shopFirestore.updateSingleField(
                shopID,
                updateField,
                fcmToken,
                {
                    Log.i("", "Update fcm success: ")
                    mShardPref.saveFcmToken(fcmToken)
                },
                { error ->
                    Log.i("", "error updating shop : $error ")
                },
            )
        }
    }
}
