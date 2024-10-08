package com.trex.laxmiemi.ui.devicedetailsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.OfflineBolt
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.ScreenLockRotation
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.Wallpaper
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R
import com.trex.rexcommon.data.DeviceActions
import com.trex.rexcommon.data.NewDevice

@Composable
fun DeviceDetails(
    device: NewDevice,
    onActionClick: (DeviceActions) -> Unit,
) {
    Column {
        DeviceDetailHeader(device)
        Spacer(modifier = Modifier.height(20.dp))
        DeviceActionRectButton(
            DeviceActionData(
                Icons.Default.Lock,
                DeviceActions.ACTION_LOCK_DEVICE,
                "Lock",
            ),
            onActionClick,
        )
        Spacer(modifier = Modifier.height(10.dp))
        DeviceActionRectButton(
            DeviceActionData(
                Icons.Default.Clear,
                DeviceActions.ACTION_UNLOCK_DEVICE,
                "Unlock",
            ),
            onActionClick,
        )

        ActionsButtonGrid(list = deviceActionDataList, onActionClick)
    }
}

@Composable
private fun DeviceDetailHeader(device: NewDevice) {
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
                text = "Device : ${device.modelNumber}",
                modifier = Modifier.padding(bottom = 5.dp),
            )

            Text(
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                text = "Custumer : ${device.costumerName} ",
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
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(list) { actionData ->
            DeviceActionButton(
                deviceActionData = actionData,
                modifier = Modifier.aspectRatio(1f),
                onActionClick = onActionClick,
            )
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
    modifier: Modifier = Modifier,
    onActionClick: (DeviceActions) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
            modifier
                .clickable { onActionClick(deviceActionData.action) }
                .padding(4.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(48.dp)
                    .background(
                        color = Color.Red,
                        shape = RoundedCornerShape(50),
                    ),
        ) {
            Icon(
                imageVector = deviceActionData.icon,
                contentDescription = deviceActionData.actionName,
                tint = Color.White,
                modifier =
                    Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = deviceActionData.actionName,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
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

val deviceActionDataList =
    DeviceActions.values().map { action ->
        when (action) {
            DeviceActions.ACTION_LOCK_DEVICE -> DeviceActionData(Icons.Default.Lock, action, "Lock")
            DeviceActions.ACTION_UNLOCK_DEVICE ->
                DeviceActionData(
                    Icons.Default.LockOpen,
                    action,
                    "Unlock",
                )

            DeviceActions.ACTION_EMI_AUDIO_REMINDER ->
                DeviceActionData(
                    Icons.Default.Notifications,
                    action,
                    "EMI Audio Reminder",
                )

            DeviceActions.ACTION_EMI_SCREEN_REMINDER ->
                DeviceActionData(
                    Icons.Default.ScreenLockRotation,
                    action,
                    "EMI Screen Reminder",
                )

            DeviceActions.ACTION_GET_PHONE_NUMBER ->
                DeviceActionData(
                    Icons.Default.Phone,
                    action,
                    "Get Phone Number",
                )

            DeviceActions.ACTION_GET_CONTACTS ->
                DeviceActionData(
                    Icons.Default.Contacts,
                    action,
                    "Get Contacts",
                )

            DeviceActions.ACTION_GET_CONTACTS_VIA_MESSAGE ->
                DeviceActionData(
                    Icons.Default.Message,
                    action,
                    "Get Contacts via Message",
                )

            DeviceActions.ACTION_OFFLINE_LOCK_UNLOCK ->
                DeviceActionData(
                    Icons.Default.OfflineBolt,
                    action,
                    "Offline Lock/Unlock",
                )

            DeviceActions.ACTION_APP_UNLOCK ->
                DeviceActionData(
                    Icons.Default.VpnKey,
                    action,
                    "App Unlock",
                )

            DeviceActions.ACTION_CAMERA_LOCK ->
                DeviceActionData(
                    Icons.Default.Camera,
                    action,
                    "Camera Lock",
                )

            DeviceActions.ACTION_CAMERA_UNLOCK ->
                DeviceActionData(
                    Icons.Default.CameraAlt,
                    action,
                    "Camera Unlock",
                )

            DeviceActions.ACTION_SET_WALLPAPER ->
                DeviceActionData(
                    Icons.Default.Wallpaper,
                    action,
                    "Set Wallpaper",
                )

            DeviceActions.ACTION_REMOVE_WALLPAPER ->
                DeviceActionData(
                    Icons.Default.Wallpaper,
                    action,
                    "Remove Wallpaper",
                )

            DeviceActions.ACTION_GET_LOCATION ->
                DeviceActionData(
                    Icons.Default.LocationOn,
                    action,
                    "Get Location",
                )

            DeviceActions.ACTION_GET_LOCATION_VIA_MESSAGE ->
                DeviceActionData(
                    Icons.Default.Message,
                    action,
                    "Get Location via Message",
                )

            DeviceActions.ACTION_REBOOT_DEVICE ->
                DeviceActionData(
                    Icons.Default.RestartAlt,
                    action,
                    "Reboot Device",
                )

            DeviceActions.ACTION_CALL_LOCK -> DeviceActionData(Icons.Default.Call, action, "Call Lock")
            DeviceActions.ACTION_CALL_UNLOCK ->
                DeviceActionData(
                    Icons.Default.CallEnd,
                    action,
                    "Call Unlock",
                )

            DeviceActions.ACTION_RESET_PASSWORD ->
                DeviceActionData(
                    Icons.Default.VpnKey,
                    action,
                    "Reset Password",
                )

            DeviceActions.ACTION_REACTIVATE_DEVICE ->
                DeviceActionData(
                    Icons.Default.Sync,
                    action,
                    "Reactivate Device",
                )

            DeviceActions.ACTION_DEACTIVATE_DEVICE ->
                DeviceActionData(
                    Icons.Default.Block,
                    action,
                    "Deactivate Device",
                )

            DeviceActions.ACTION_GET_DEVICE_INFO ->
                DeviceActionData(
                    Icons.Default.Info,
                    action,
                    "Get Device Info",
                )

            DeviceActions.ACTION_GET_UNLOCK_CODE ->
                DeviceActionData(
                    Icons.Default.LockOpen,
                    action,
                    "Get Unlock Code",
                )

            DeviceActions.ACTION_REMOVE_DEVICE ->
                DeviceActionData(
                    Icons.Default.Delete,
                    action,
                    "Remove Device",
                )
        }
    }

@Preview(name = "DeviceDetailsPreview", showBackground = true, showSystemUi = true)
@Composable
fun DeviceDetailsPreview() {
//    DeviceDetails(Device(deviceId = "OPPO VIVO", customerName = "Rahul"))
}
