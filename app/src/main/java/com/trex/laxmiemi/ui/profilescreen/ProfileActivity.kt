package com.trex.laxmiemi.ui.profilescreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.trex.rexnetwork.domain.firebasecore.firesstore.ShopFirestore
import com.trex.rexnetwork.utils.SharedPreferenceManager

class ProfileActivity : ComponentActivity() {
    val shopDb = ShopFirestore()
    lateinit var localDb: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: ProfileViewModel by viewModels()

        setContent {
            ProfileScreen(vm)
        }
    }
}
