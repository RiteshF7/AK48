package com.trex.laxmiemi.ui.homescreen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.trex.laxmiemi.ui.components.SESHomeScreen

class HomescreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val homeScreenViewmodel by viewModels<HomeScreenViewmodel>();
        setContent {
            Box(Modifier.padding(top = 10.dp)) {
                SESHomeScreen(homeScreenViewmodel)
            }
        }

    }
}