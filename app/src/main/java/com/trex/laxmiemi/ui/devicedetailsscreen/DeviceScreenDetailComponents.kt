package com.trex.laxmiemi.ui.devicedetailsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R
import com.trex.laxmiemi.data.firebase.firestore.Device
import com.trex.rexcommon.data.DeviceActions

@Composable
fun DeviceDetails(
    device: Device,
    onActionClick: (DeviceActions) -> Unit,
) {
    Column {
        DeviceDetailHeader(device)
        Spacer(modifier = Modifier.height(20.dp))
        DeviceActionRectButton(
            DeviceActionData(
                Icons.Default.Lock,
                DeviceActions.LOCK_DEVICE,
                "Lock",
            ),
            onActionClick,
        )
        Spacer(modifier = Modifier.height(10.dp))
        DeviceActionRectButton(
            DeviceActionData(
                Icons.Default.Clear,
                DeviceActions.UNLOCK_DEVICE,
                "Unlock",
            ),
            onActionClick,
        )

        ActionsButtonGrid(list = dummyData, onActionClick)
    }
}

@Composable
private fun DeviceDetailHeader(device: Device?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.blue_900)),
    ) {
        Box {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                tint = Color.White,
                modifier =
                Modifier
                    .padding(10.dp)
                    .height(100.dp)
                    .width(100.dp),
            )
        }
        Column {
            Text(
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                text = "Device : ${device?.fcmTokenId}",
                modifier = Modifier.padding(bottom = 5.dp),
            )

            Text(
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                text = "Custumer : ${device?.customerName} ",
                modifier = Modifier.padding(bottom = 5.dp),
            )
        }
    }
}

@Composable
fun ActionsButtonGrid(
    list: List<DeviceActionData>,
    onActionClick: (DeviceActions) -> Unit,
) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        list.chunked(3).forEach {
            Row(
                Modifier
                    .fillMaxWidth(),
            ) {
                it.forEach {
                    DeviceActionButton(deviceActionData = it, Modifier.weight(1f), onActionClick)
                }
            }
        }
    }
}

data class DeviceActionData(
    val icon: ImageVector,
    val action: DeviceActions,
    val actionName: String,
)

@Composable
fun DeviceActionButton(
    deviceActionData: DeviceActionData,
    modifier: Modifier,
    onActionClick: (DeviceActions) -> Unit,
) {
    Box(Modifier.clickable { onActionClick(deviceActionData.action) }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(20.dp),
        ) {
            Box(
                modifier =
                    Modifier.background(
                        color = Color.Red,
                        shape = RoundedCornerShape(50.dp),
                    ),
            ) {
                Icon(
                    tint = Color.White,
                    modifier =
                    Modifier
                        .align(Alignment.Center)
                        .padding(10.dp),
                    imageVector = deviceActionData.icon,
                    contentDescription = "",
                )
            }
            Text(text = deviceActionData.actionName)
        }
    }
}

@Composable
fun DeviceActionRectButton(
    deviceActionData: DeviceActionData,
    onActionClick: (DeviceActions) -> Unit,
) {
    Box(Modifier.clickable { onActionClick(deviceActionData.action) }) {
        Column(
            modifier =
                Modifier
                    .padding(horizontal = 10.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.red_400),
                            shape = RoundedCornerShape(3.dp),
                        ),
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.Default.Lock,
                        contentDescription = "",
                    )
                    Text(
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        text = deviceActionData.actionName,
                        modifier =
                            Modifier
                                .padding(10.dp),
                    )
                }
            }
        }
    }
}

val dummyData =
    listOf(
        DeviceActionData(
            Icons.Default.Lock,
            DeviceActions.LOCK_DEVICE,
            "Lock",
        ),
        DeviceActionData(
            Icons.Default.Lock,
            DeviceActions.UNLOCK_DEVICE,
            "Unlock",
        )
    )

@Preview(name = "DeviceDetailsPreview", showBackground = true, showSystemUi = true)
@Composable
fun DeviceDetailsPreview() {
//    DeviceDetails(Device(deviceId = "OPPO VIVO", customerName = "Rahul"))
}
