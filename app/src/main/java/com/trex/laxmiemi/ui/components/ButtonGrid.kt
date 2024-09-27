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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R

enum class ButtonActions {
    AddCustomer,
    SES20QR,
    TotalCustomer,
    BalanceKeys,
    CallForService,
    InstallationVideo,
    OldQR,
    AppShare,
    UserProfile
}

data class GridButton(val action: ButtonActions, val key: String, val icon: ImageVector)
@Composable
fun ButtonGrid(onClick: (action: ButtonActions) -> Unit) {

    val buttons = listOf(
        GridButton(ButtonActions.AddCustomer, "Add Customer", Icons.Default.Add),
        GridButton(ButtonActions.SES20QR, "SES 2.0 QR", Icons.Default.Warning),
        GridButton(ButtonActions.TotalCustomer, "Total Customer", Icons.Default.Person),
        GridButton(ButtonActions.BalanceKeys, "Balance Keys", Icons.Default.Warning),
        GridButton(ButtonActions.CallForService, "Call For Service", Icons.Default.Call),
        GridButton(ButtonActions.InstallationVideo, "Installation Video", Icons.Default.PlayArrow),
        GridButton(ButtonActions.OldQR, "Old QR", Icons.Default.Warning),
        GridButton(ButtonActions.AppShare, "App Share", Icons.Default.Share),
        GridButton(ButtonActions.UserProfile, "User Profile", Icons.Default.AccountCircle)
    );



    Column(modifier = Modifier.padding(16.dp)) {
        buttons.chunked(3).forEach { rowButtons ->
            ButtonRow(rowButtons,onClick)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// -- Single Row in the Button Grid --
@Composable
fun ButtonRow(buttons: List<GridButton>,onAction:(action:ButtonActions)->Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        buttons.forEach { button->
            ActionButton(button, Modifier.weight(1f),onAction)
        }
    }
}


// -- Action Button in the Grid --
@Composable
fun ActionButton(
    button: GridButton,
    modifier: Modifier,
    onClick:(action:ButtonActions)->Unit
) {
    Button(
        onClick = { onClick(button.action) },
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = button.icon, contentDescription = button.key, tint = Color(0xFF3F51B5))
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.opensans_bold))
                ),
                text = button.key,
                color = Color(0xFF3F51B5),
            )
        }
    }
}
