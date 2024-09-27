package com.trex.laxmiemi.ui.components

import androidx.compose.material3.*
import androidx.compose.ui.text.font.Font
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R

@Composable
fun Banner(
    backgroundImage: Painter, verticalText: String, rightImage: Painter
) {

    EmisafeScreen()


}


@Composable
fun UserIcon(name: String) {
    Box(Modifier.padding(start = 10.dp, end = 10.dp)) {
        Box(Modifier.background(Color.White, shape = RoundedCornerShape(10.dp))) {
            Icon(
                modifier = Modifier.padding(2.dp),
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "user icon"
            )
        }
    }
}


@Composable
fun DealerCodeHolder(dealerCode: String) {
    Row(
        modifier = Modifier
            .background(colorResource(id = R.color.grey_100), shape = RoundedCornerShape(50.dp))
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            text = "${dealerCode}",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.opensans_bold)), // Use OpenSans Regular directly
                fontSize = 20.sp,
                color = colorResource(id = R.color.blue_600)
            )
        )
    }
}

@Preview(name = "Greeting Preview", showBackground = true)
@Composable
fun GreetingPreview() {
    DealerCode()
}

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
                .padding(horizontal = 20.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween, // Space between elements
            verticalAlignment = Alignment.CenterVertically // Align items vertically (if needed)
        ) {

            Text(
                text = "Dealer Code",
                color = Color.White,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.opensans_bold)), // Use OpenSans Regular directly
                    fontSize = 20.sp
                )
            )
            DealerCodeHolder("123456")



        }


    }

}




@Composable
fun EmisafeScreen() {
    MaterialTheme(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE6E9F0))
        ) {
            Header()
            DealerCode()
            ButtonGrid()
            Spacer(modifier = Modifier.weight(1f))
            LogoutButton()
        }
    }
}

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
                        .height(35.dp)
                        .width(35.dp),
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock Icon",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    fontSize = 26.sp,
                    text = "SECURE EMI SHIELD",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.opensans_bold)) // Use Roboto Bold directly
                    )
                )
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = "Secure emi payments",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.opensans_bold)), // Use OpenSans Regular directly
                        fontSize = 20.sp
                    )
                )
            }
        }
    }
}

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


@Composable
fun ButtonGrid() {
    val buttons = listOf(
        "Add Customer" to Icons.Default.Add,
        "Emisafe 2.0 QR" to Icons.Default.Warning,
        "Total Customer" to Icons.Default.Person,
        "Balance Keys" to Icons.Default.Warning,
        "Call For Service" to Icons.Default.Call,
        "Installation Video" to Icons.Default.PlayArrow,
        "Old QR" to Icons.Default.Warning,
        "App Share" to Icons.Default.Share,
        "User Profile" to Icons.Default.AccountCircle
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        buttons.chunked(3).forEach { rowButtons ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowButtons.forEach { (text, icon) ->
                    Button(
                        onClick = { /* TODO: Implement click action */ },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = text,
                                tint = Color(0xFF3F51B5)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = text,
                                color = Color(0xFF3F51B5),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

