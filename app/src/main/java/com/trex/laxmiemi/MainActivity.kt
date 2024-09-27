package com.trex.laxmiemi

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.FirebaseApp
import com.trex.laxmiemi.ui.components.HomeScreen

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this);
        val mainViewModel :MainActivityViewModel by viewModels()
        mainViewModel.firebaseUser.observe(this){
            if (it != null){
                mainViewModel.checkIfShopExists()
                setContent {
                    MyApp(mainViewModel)
                }
            }
            else{
                val sendOtpActivity = Intent(this, OtpSendActivity::class.java)
                startActivity(sendOtpActivity)
                finish()
            }
        }

    }


}

@Composable
fun MyApp(mainViewModel: MainActivityViewModel) {
    Box(Modifier.padding(top = 20.dp)) {
        HomeScreen(mainViewModel)
    }

}