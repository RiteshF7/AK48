package com.trex.laxmiemi.ui.qrcodescreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lightspark.composeqr.DotShape
import com.lightspark.composeqr.QrCodeColors
import com.lightspark.composeqr.QrCodeView
import com.trex.laxmiemi.ui.theme.LaxmiEmiTheme

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
    Box(modifier = modifier.fillMaxSize()) {
        PurpleAndGold(Modifier.align(Alignment.Center))
    }
}

private val qrCodeData =
    """
    {
      "android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME": "com.trex.rexandroidsecureclient/com.trex.rexandroidsecureclient.deviceowner.RexDeviceOwnerReceiver",
      "android.app.extra.PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM":"Bu9M77FqM7wAy-pFf7Asb0kEPb1qbJSWmU9ef5RGzo0",
      "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION": "http://192.168.0.165:8080/file"
    }
    """.trimIndent()

@Composable
fun PurpleAndGold(modifier: Modifier) {
    val purple = Color(0xFF552583)
    val gold = Color(0xFFFDB927)
    QrCodeView(
        data = qrCodeData,
        modifier = modifier.size(300.dp),
        colors =
            QrCodeColors(
                background = purple,
                foreground = gold,
            ),
        dotShape = DotShape.Square,
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
}
