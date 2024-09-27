package com.trex.laxmiemi.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R

@Composable
fun ButtonGrid(onClick: (action: String) -> Unit) {
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
            ButtonRow(rowButtons,onClick)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// -- Single Row in the Button Grid --
@Composable
fun ButtonRow(buttons: List<Pair<String, androidx.compose.ui.graphics.vector.ImageVector>>,onAction:(action:String)->Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        buttons.forEach { (text, icon) ->
            ActionButton(
                text = text, icon = icon, Modifier.weight(1f),onAction)
        }
    }
}


// -- Action Button in the Grid --
@Composable
fun ActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier,
    onClick:(action:String)->Unit
) {
    Button(
        onClick = { onClick(text) },
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
