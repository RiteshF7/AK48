package com.trex.laxmiemi.ui.profilescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R
import com.trex.laxmiemi.ui.components.RexActionButton

@Preview
@Composable
fun some() {
}

@Composable
fun ProfileScreen(vm: ProfileViewModel) {
    val uiState by vm.profileState

    Column(
        modifier =
            Modifier
                .background(color = Color.Black.copy(alpha = 0.85f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        ProfileHeader(uiState)
        HorizontalDivider(thickness = 2.dp)
        ProfileDetails(uiState)
    }
}

@Composable
private fun ProfileHeader(uiState: ProfileState) {
    Column(
        modifier = Modifier.padding(36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Card(
            colors =
                CardDefaults.cardColors(
                    containerColor = Color.Green.copy(alpha = 0.1f),
                    contentColor = Color.White,
                ),
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "",
                tint = Color(0xFF00C853),
                modifier =
                    Modifier
                        .size(92.dp)
                        .padding(5.dp),
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            textAlign = TextAlign.Center,
            text = uiState.shopName,
            style =
                TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                ),
        )
    }
}

@Composable
private fun ProfileDetails(shop: ProfileState) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextInfo("Shop ID:", shop.shopId)
        TextInfo("Shop name:", shop.shopName)
        TextInfo("Token Balance:", shop.tokenBalance)
        TextInfo("Active devices:", shop.activeDevice)
        TextInfo("DeActivated devices:", shop.deactivatedDevices)

        Spacer(Modifier.weight(1f))
        HorizontalDivider(Modifier.height(2.dp))
        RexActionButton("Logout") {
        }
    }
}

@Composable
private fun TextInfo(
    key: String,
    value: String,
) {
    Column {
        Row(Modifier.padding(20.dp)) {
            Text(
                text = key,
                style =
                    TextStyle(
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 16.sp,
                        color = Color.White,
                    ),
            )
            Spacer(Modifier.weight(1f))

            Text(
                text = value,
                style =
                    TextStyle(
                        fontFamily = FontFamily(Font(R.font.opensans_bold)),
                        fontSize = 16.sp,
                        color = colorResource(R.color.primary),
                    ),
            )
        }
        HorizontalDivider(thickness = 2.dp)
    }
}

@Composable
private fun DeviceCountItem(
    title: String,
    count: Int,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.primary),
                ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = count.toString(),
            style =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.opensans_bold)),
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.primary),
                ),
        )
    }
}
