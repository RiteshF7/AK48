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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter // For loading images
import androidx.compose.ui.text.style.TextAlign
import com.trex.laxmiemi.R

@Composable
fun Banner(
    backgroundImage: Painter,
    verticalText: String,
    rightImage: Painter
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        // Background image
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        // Content overlay
        Row(
            modifier = Modifier
                .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Space between text and right image
        ) {
            Image(
                painter = rightImage,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp) // Adjust size as needed
                    .align(Alignment.CenterVertically) // Center vertically
            )



        }
    }
}


@Composable
fun dealerCodeHolder(dealerCode: String) {
    Box(modifier = Modifier.background(Color.Blue, shape = RoundedCornerShape(5.dp))) {
        Text(
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            text = dealerCode,
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