package com.trex.laxmiemi.ui.profilescreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.trex.rexnetwork.domain.firebasecore.firesstore.ShopFirestore
import com.trex.rexnetwork.utils.SharedPreferenceManager

class ProfileActivity : ComponentActivity() {
    val shopDb = ShopFirestore()
    lateinit var localDb: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        localDb = SharedPreferenceManager(this)
        localDb.getShopId()?.let { shopId ->
            shopDb.getShopById(shopId, { shop ->
                setContent {
                    ProfileScreen(shop)
                }
            }, {
                finish()
            })
        }
    }
}
