package com.trex.laxmiemi.ui.devicescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R
import com.trex.laxmiemi.handlers.ShopActionExecutor
import com.trex.laxmiemi.ui.createdevicescreen.EditDeviceInfoActivity
import com.trex.laxmiemi.ui.devicedetailsscreen.DeviceDetailActivity
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.Actions
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
    val context = LocalContext.current
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clickable {
                    DeviceDetailActivity.go(context, deviceInfo)
                },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
    ) {
        if (deviceInfo.costumerName.isBlank()) {
            Column(
                Modifier.fillMaxWidth().padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "",
                    tint = colorResource(R.color.red_300),
                    modifier =
                        Modifier
                            .size(92.dp),
                )

                Text(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                    textAlign = TextAlign.Center,
                    text = "New Device Detected\n Please complete registration",
                    color = colorResource(R.color.red_300),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(Modifier.padding(10.dp))
                InfoRow("IMEI 1:", deviceInfo.imeiOne, Color.White)
                InfoRow("IMEI 2:", deviceInfo.imeiTwo, Color.White)
                Spacer(Modifier.padding(10.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        EditDeviceInfoActivity.go(context, deviceInfo.deviceId)
                    },
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.red_300), // Background color
                            contentColor = Color.White, // Text color
                            disabledContainerColor = Color.Gray, // Background color when disabled
                            disabledContentColor = Color.LightGray, // Text color when disabled
                        ),
                ) {
                    Text("Complete Device Registration!")
                }
            }
        } else {
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
                InfoRow("IMEI 1:", deviceInfo.imeiOne, Color.White)
                InfoRow("IMEI 2:", deviceInfo.imeiTwo, Color.White)
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
                        onClick = {
                            ShopActionExecutor(context = context).sendActionToClient(
                                ActionMessageDTO(deviceInfo.fcmToken, Actions.ACTION_LOCK_DEVICE),
                            )
                        },
                        modifier =
                            Modifier
                                .weight(1f)
                                .padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary)), // Green
                    ) {
                        Text("Lock", color = Color.White)
                    }
                    Button(
                        onClick = {
                            ShopActionExecutor(context = context).sendActionToClient(
                                ActionMessageDTO(deviceInfo.fcmToken, Actions.ACTION_UNLOCK_DEVICE),
                            )
                        },
                        modifier =
                            Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.red_300)), // Red
                    ) {
                        Text("Unlock", color = Color.White)
                    }
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
