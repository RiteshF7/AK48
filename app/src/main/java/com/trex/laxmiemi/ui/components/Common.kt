package com.trex.laxmiemi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R

// -- Logout Button --
@Composable
fun SesActionButton(text:String,onClick:()->Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373))
    ) {
        Text(
            text,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun RoundedCardText(dealerCode: String) {
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

@Composable
fun CircleIcon(bgColor: Color, icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(bgColor)
            .padding(8.dp)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(35.dp),
            imageVector = icon,
            contentDescription = "Lock Icon",
            tint = Color.White
        )
    }
}