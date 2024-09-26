package com.trex.laxmiemi.ui.components

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter // For loading images
import androidx.compose.ui.text.style.TextAlign
import com.trex.laxmiemi.R

@Composable
fun Banner(
    backgroundImage: Painter, verticalText: String, rightImage: Painter
) {
    Column(modifier = Modifier.background(colorResource(id = R.color.grey_100))) {
        Box(

            modifier = Modifier.fillMaxWidth()

        ) {
            // Background image
            Image(
                painter = backgroundImage,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )


        }

        Row(
            Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            dealerCodeHolder(dealerCode = "112233")
        }
    }
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
fun dealerCodeHolder(dealerCode: String) {
    Row(
        modifier = Modifier.background(Color.Blue, shape = RoundedCornerShape(5.dp)).padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserIcon(name = "Ritesh")

        Text(
            fontSize = 15.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            text = "Dealer code : ${dealerCode}",
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview(name = "Greeting Preview", showBackground = true)
@Composable
fun GreetingPreview() {
    Banner(
        backgroundImage = painterResource(id = R.drawable.banner_bg_2),
        verticalText = "TREX EMI",
        rightImage = painterResource(
            id = R.drawable.mylogo
        )
    )
}