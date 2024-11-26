package com.trex.laxmiemi.ui.qrcodescreen

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trex.laxmiemi.ui.theme.LaxmiEmiTheme
import com.trex.rexnetwork.R
import com.trex.rexnetwork.utils.SharedPreferenceManager

class ScanQrActivity : ComponentActivity() {
    private lateinit var mSharefPref: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSharefPref = SharedPreferenceManager(this)
        val shopId = mSharefPref.getShopId()
        val vm: ScanQrViewmodel by viewModels()
        shopId?.let { id ->
            vm.getQrBitMap(id)
        } ?: { finish() }

        enableEdgeToEdge()
        setContent {
            LaxmiEmiTheme {
                val uiState by vm.scanQrUiState
                when (uiState) {
                    is ScanQrUiState.Failed -> {
                        finish()
                    }

                    ScanQrUiState.Loading -> {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color = Color.Black.copy(alpha = 0.85f)),
                        ) {
                            CircularProgressIndicator(color = colorResource(R.color.primary))
                        }
                    }

                    is ScanQrUiState.Success -> {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            MyScreen(
                                Modifier.padding(innerPadding),
                                (uiState as ScanQrUiState.Success).bitmap,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MyScreen(
    modifier: Modifier,
    bitmap: Bitmap,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .fillMaxHeight(),
    ) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            Image(
                modifier =
                    Modifier
                        .width(300.dp)
                        .height(300.dp),
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "some useful description",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
}
