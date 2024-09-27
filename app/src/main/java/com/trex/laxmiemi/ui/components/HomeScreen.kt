package com.trex.laxmiemi.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R
import com.trex.laxmiemi.ui.homescreen.HomeScreenViewmodel




// -- Main Screen Composable --
@Composable
fun SESHomeScreen(homeScreenViewmodel: HomeScreenViewmodel) {
    val scrollState = rememberScrollState()
    val dealerCode = homeScreenViewmodel.dealerCode.observeAsState("------")

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE6E9F0))
                .verticalScroll(scrollState)
        ) {
            Header()
            DealerCode(dealerCode.value)
            ButtonGrid(){
                Log.i("button onClick", "SESHomeScreen: ${it}")
            }
            Spacer(modifier = Modifier.weight(1f))
            SesActionButton(text = "Logout") {

            }
        }
    }
}


// -- Dealer Code Component --
@Composable
fun DealerCode(dealerCode: String = "------") {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = colorResource(id = R.color.blue_800))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dealer Code",
                color = Color.White,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.opensans_bold)),
                    fontSize = 20.sp
                )
            )
            RoundedCardText(dealerCode)
        }
    }
}

// -- Header Component --
@Composable
fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = colorResource(id = R.color.blue_900))
            .padding(16.dp)
    ) {
        Row(
            Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIcon(Color(0xFF8BC34A),Icons.Default.Lock)
            Spacer(modifier = Modifier.width(16.dp))
            HeaderText()
        }
    }
}

// -- Circle Icon in Header --


// -- Texts in Header --
@Composable
fun HeaderText() {
    Column {
        Text(
            fontSize = 26.sp,
            text = "SECURE EMI SHIELD",
            color = Color.White,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.opensans_bold))
            )
        )
        Text(
            text = "Secure emi payments",
            color = Color.White,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.opensans_bold)),
                fontSize = 20.sp
            )
        )
    }
}




@Preview
@Composable
fun PreviewScreen() {
}
