package com.trex.laxmiemi

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.FirebaseApp
import com.trex.laxmiemi.databinding.ActivityMainBinding
import com.trex.laxmiemi.ui.homescreen.HomescreenActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this);
        val mainViewModel by viewModels<MainActivityViewModel>();
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.firebaseUser.observe(this) { firebaseUser->
            if (firebaseUser != null){

                binding.tvMobile.text = "User Phone Number\n${firebaseUser.phoneNumber}"
                startActivity(Intent(this,HomescreenActivity::class.java))

            }
            else{
                startActivity(Intent(this, OtpSendActivity::class.java))
                finish()
            }

        }

        binding.btnSignOut.setOnClickListener {
            mainViewModel.signOut()
        }
//
//        binding.btnT.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO) {
//                DummyDatabaseScript().testFirestoreOperations()
//            }
//        }
    }


}