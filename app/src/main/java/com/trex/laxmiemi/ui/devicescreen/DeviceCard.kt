package com.trex.laxmiemi.ui.devicescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R
import com.trex.rexnetwork.data.NewDevice

data class DeviceInfo(
    val customerName: String,
    val email: String,
    val phoneNumber: String,
    val loanNumber: String,
    val deviceName: String,
    val purchaseDate: String,
)

@Composable
fun DeviceCard(deviceInfo: NewDevice) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
        ) {
            Text(
                text = deviceInfo.costumerName,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(8.dp))
            InfoRow("Device:", deviceInfo.modelNumber, Color.White)
            InfoRow("Email:", deviceInfo.email, Color.White)
            InfoRow("Phone:", deviceInfo.costumerPhone, Color.White)
            InfoRow("Loan Number:", deviceInfo.loanNumber, colorResource(R.color.red_400))
            InfoRow(
                "Purchase Date:",
                deviceInfo.createdAt.toDate().toString(),
                Color(0xFF64B5F6),
            ) // Light Blue as secondary color

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    onClick = { /* TODO: Implement lock functionality */ },
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary)), // Green
                ) {
                    Text("Lock", color = Color.White)
                }
                Button(
                    onClick = { /* TODO: Implement unlock functionality */ },
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.red_300)), // Red
                ) {
                    Text("Unlock", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    textColor: Color,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.width(100.dp),
        )
        Text(
            text = value,
            color = textColor,
            fontSize = 18.sp,
        )
    }
}

// Example usage
@Composable
@Preview
fun DeviceCardExample() {
    val sampleDevice =
        DeviceInfo(
            customerName = "John Doe",
            email = "john.doe@example.com",
            phoneNumber = "+1 (555) 123-4567",
            loanNumber = "L123456789",
            deviceName = "iPhone 12 Pro",
            purchaseDate = "2023-05-15",
        )

    Column(Modifier.background(Color.Black.copy(alpha = 0.85f))) {
    }
}
