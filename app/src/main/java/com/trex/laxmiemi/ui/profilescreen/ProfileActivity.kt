package com.trex.laxmiemi.ui.profilescreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import com.trex.laxmiemi.ui.loginscreen.OtpSendActivity
import com.trex.rexnetwork.domain.firebasecore.firesstore.ShopFirestore
import com.trex.rexnetwork.utils.SharedPreferenceManager

class ProfileActivity : ComponentActivity() {
    val shopDb = ShopFirestore()
    lateinit var localDb: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: ProfileViewModel by viewModels()

        setContent {

            ProfileScreen(vm) {
                val intent =
                    Intent(this, OtpSendActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                startActivity(intent)
                finish()
            }
        }
    }
}
