package com.trex.laxmiemi.ui.profilescreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.trex.laxmiemi.ui.loginscreen.LoginWithEmailActivity
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexnetwork.domain.firebasecore.firesstore.ShopFirestore
import com.trex.rexnetwork.utils.SharedPreferenceManager

class ProfileActivity : ComponentActivity() {
    val shopDb = ShopFirestore()
    lateinit var localDb: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: ProfileViewModel by viewModels()
        localDb = SharedPreferenceManager(this)

        setContent {
            ProfileScreen(vm) {
                localDb.clearPreference {
                    CommonConstants.shodId = ""
                    val intent =
                        Intent(this, LoginWithEmailActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
