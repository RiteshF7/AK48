package com.trex.laxmiemi.ui.qrcodescreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trex.laxmiemi.ui.theme.LaxmiEmiTheme
import com.trex.laxmiemi.utils.QrUtils

class ScanQrActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaxmiEmiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
private fun MyScreen(modifier: Modifier) {
    val qrCodeImageBitmap =
        QrUtils()
            .getQrBitmap()
            .asImageBitmap()
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .fillMaxHeight(),
    ) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            Image(
                modifier = Modifier.width(300.dp).height(300.dp),
                bitmap = qrCodeImageBitmap,
                contentDescription = "some useful description",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
}
