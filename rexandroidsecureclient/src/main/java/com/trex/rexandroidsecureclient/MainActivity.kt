package com.trex.rexandroidsecureclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.google.firebase.FirebaseApp
import com.trex.rexandroidsecureclient.ui.theme.LaxmiEmiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        val vm by viewModels<MainActivityViewModel>()

        setContent {
            LaxmiEmiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        GetTokenButton(vm = vm)
                    }
                }
            }
        }
    }
}

@Composable
fun GetTokenButton(vm: MainActivityViewModel) {
    val token = vm.currentToken.observeAsState()
    Button(
        onClick = {
            try {
                vm.getCurrentToken()
            } catch (e: Exception) {
                Log.e("FCM", "Error saving token", e)
            }
        },
    ) {
        Text(text = token.value?:"Null")
    }
}
