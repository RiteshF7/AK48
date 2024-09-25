package com.trex.laxmiemi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.trex.laxmiemi.databinding.ActivityMainBinding
import com.trex.laxmiemi.ui.theme.LaxmiEmiTheme
import com.trex.rexcommon.data.RetrofitClient

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this);
        val mainViewModel by viewModels<MainActivityViewModel>();
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = Firebase.auth

        if (mAuth.currentUser != null)
            binding.tvMobile.text = "User Phone Number\n${mAuth.currentUser!!.phoneNumber}"

        binding.btnSignOut.setOnClickListener {
            mAuth.signOut()
//            startActivity(Intent(this, OtpSendActivity::class.java))
            finish()
        }
    }



}