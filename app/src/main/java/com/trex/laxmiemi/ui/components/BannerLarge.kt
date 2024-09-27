package com.trex.laxmiemi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R

// -- Dealer Code Holder Component --
@Composable
fun DealerCodeHolder(dealerCode: String) {
    Row(
        modifier = Modifier
            .background(colorResource(id = R.color.grey_100), shape = RoundedCornerShape(50.dp))
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = dealerCode,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.opensans_bold)),
                fontSize = 20.sp,
                color = colorResource(id = R.color.blue_600)
            )
        )
    }
}

// -- Dealer Code Component --
@Composable
fun DealerCode() {
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
            DealerCodeHolder("123456")
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
            CircleIcon()
            Spacer(modifier = Modifier.width(16.dp))
            HeaderText()
        }
    }
}

// -- Circle Icon in Header --
@Composable
fun CircleIcon() {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(Color(0xFF8BC34A))
            .padding(8.dp)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(35.dp),
            imageVector = Icons.Default.Lock,
            contentDescription = "Lock Icon",
            tint = Color.White
        )
    }
}

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

// -- Logout Button --
@Composable
fun LogoutButton() {
    Button(
        onClick = { /* TODO: Implement logout action */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373))
    ) {
        Text(
            "Logout",
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

// -- Button Grid for Actions --
@Composable
fun ButtonGrid() {
    val buttons = listOf(
        "Add Customer" to Icons.Default.Add,
        "SES 2.0 QR" to Icons.Default.Warning,
        "Total Customer" to Icons.Default.Person,
        "Balance Keys" to Icons.Default.Warning,
        "Call For Service" to Icons.Default.Call,
        "Installation Video" to Icons.Default.PlayArrow,
        "Old QR" to Icons.Default.Warning,
        "App Share" to Icons.Default.Share,
        "User Profile" to Icons.Default.AccountCircle
    )

    Column(modifier = Modifier.padding(16.dp)) {
        buttons.chunked(3).forEach { rowButtons ->
            ButtonRow(rowButtons)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// -- Single Row in the Button Grid --
@Composable
fun ButtonRow(buttons: List<Pair<String, androidx.compose.ui.graphics.vector.ImageVector>>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        buttons.forEach { (text, icon) ->
            ActionButton(
                text = text, icon = icon,Modifier.weight(1f))
        }
    }
}

// -- Action Button in the Grid --
@Composable
fun ActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier
) {
    Button(
        onClick = { /* TODO: Implement click action */ },
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = icon, contentDescription = text, tint = Color(0xFF3F51B5))
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.opensans_bold))
                ),
                text = text,
                color = Color(0xFF3F51B5),
            )
        }
    }
}

// -- Main Screen Composable --
@Composable
fun SESHomeScreen() {
    val scrollState = rememberScrollState()

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE6E9F0))
                .verticalScroll(scrollState)
        ) {
            Header()
            DealerCode()
            ButtonGrid()
            Spacer(modifier = Modifier.weight(1f))
            LogoutButton()
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {
    SESHomeScreen()
}
