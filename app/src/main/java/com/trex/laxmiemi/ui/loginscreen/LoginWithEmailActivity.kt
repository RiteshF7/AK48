package com.trex.laxmiemi.ui.loginscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.trex.laxmiemi.MainActivity
import com.trex.laxmiemi.ui.signupscreen.SignUpActivity
import com.trex.laxmiemi.utils.CommonConstants
import com.trex.rexnetwork.utils.SharedPreferenceManager
import com.trex.rexnetwork.utils.startMyActivity

class LoginWithEmailActivity : ComponentActivity() {
    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        sharedPreferenceManager = SharedPreferenceManager(this)
        val vm by viewModels<LoginViewModel>()

        if (!sharedPreferenceManager.getShopId().isNullOrBlank()) {
            CommonConstants.shodId = sharedPreferenceManager.getShopId()!!
            this.startMyActivity(MainActivity::class.java, true)
            finish()
            return
        }
        setContent {
            LoginScreen(vm, {
                startActivity(Intent(this, SignUpActivity::class.java))
            }) { user ->
                sharedPreferenceManager.saveShopId(user.shopId)
                CommonConstants.shodId = user.shopId
                recreate()
            }
        }
    }
}
