package com.trex.laxmiemi.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ButtonActions {
    AddCustomer,
    SES20QR,
    TotalCustomer,
    DelayedCustomers,
    DeletedDevices,
    tokenBalance,
    CallForService,
    InstallationVideo,
    OldQR,
    AppShare,
    UserProfile,
}

data class GridButton(
    val icon: ImageVector,
    val action: ButtonActions,
    val title: String,
)

@Composable
fun ButtonGrid(onClick: (action: ButtonActions) -> Unit) {
    val menuItems =
        listOf(
            GridButton(Icons.Default.Add, ButtonActions.AddCustomer, "Register new customer"),
            GridButton(Icons.Default.QrCode, ButtonActions.SES20QR, "Generate QR code"),
            GridButton(Icons.Default.Group, ButtonActions.TotalCustomer, "All devices"),
            GridButton(Icons.Default.Group, ButtonActions.TotalCustomer, "Overdue payments"),
            GridButton(Icons.Default.Call, ButtonActions.CallForService, "Get support"),
            GridButton(Icons.Default.PlayArrow, ButtonActions.InstallationVideo, "Watch guide"),
            GridButton(Icons.Default.QrCodeScanner, ButtonActions.OldQR, "Legacy QR codes"),
            GridButton(Icons.Default.Share, ButtonActions.AppShare, "Share application"),
            GridButton(Icons.Default.Person, ButtonActions.UserProfile, "Manage account"),
        )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(menuItems) { item ->
            MenuItemCard(item)
        }
    }
}

@Composable
fun MenuItemCard(item: GridButton) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)

    Card(
        modifier =
            Modifier
                .scale(scale)
                .clickable(
                    onClick = { /* Handle click */ },
                ),
        shape = RoundedCornerShape(8.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = Color(0xFF2A2A2A),
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = Color(0xFF00C853),
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = item.title,
                fontSize = 10.sp,
                color = Color.Gray,
            )
        }
    }
}
