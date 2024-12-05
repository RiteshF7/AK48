package com.trex.laxmiemi.ui.loginscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        val signUpIntent = Intent(this, SignUpActivity::class.java)

        if (!sharedPreferenceManager.getShopId().isNullOrBlank()) {
            CommonConstants.shodId = sharedPreferenceManager.getShopId()!!
            this.startMyActivity(MainActivity::class.java, true)
            finish()
            return
        }
        setContent {
            Box(
                modifier =
                    Modifier
                        .background(color = Color.Black.copy(alpha = 0.85f))
                        .fillMaxSize(),
            ) {
                LoginScreen(vm, {
                    startActivity(signUpIntent)
                }) { user ->
                    sharedPreferenceManager.saveShopId(user.shopId)
                    CommonConstants.shodId = user.shopId
                    recreate()
                }
            }
        }
    }
}
