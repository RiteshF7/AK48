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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.ScreenLockRotation
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R
import com.trex.rexnetwork.data.Actions
import com.trex.rexnetwork.data.NewDevice

/*

*/
@Composable
fun DeviceDetails(
    device: NewDevice,
    onActionClick: (Actions) -> Unit,
) {
    Column(Modifier.background(color = Color.Black.copy(alpha = 0.85f))) {
        DeviceDetailHeader(device)
        TextHeader("CONTROL PANEL")
        ActionsButtonGrid(list = deviceActionDataList as List<DeviceActionData>, onActionClick)
    }
}

@Composable
fun TextHeader(text: String) {
    HorizontalDivider()
    Row(
        modifier = Modifier.padding(20.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.SettingsSuggest,
            contentDescription = "",
            tint = colorResource(R.color.primary),
            modifier =
                Modifier
                    .height(25.dp)
                    .width(25.dp),
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier =
                Modifier.padding(top = 2.dp),
            style =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.opensans_bold)),
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.white),
                ),
        )
    }

    HorizontalDivider()
}

@Composable
fun QuickActionsList() {
    LazyRow {
        items(listOf(1, 2, 3, 4, 5, 6, 7, 8)) {
            QuickActionIItem()
        }
    }
}

@Composable
fun QuickActionIItem() {
    Card(
        modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.1f),
                contentColor = Color.White,
            ),
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Lock device",
            style =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.opensans_medium)),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.white),
                ),
        )
    }
}

@Composable
private fun DeviceDetailHeader(device: NewDevice) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
    ) {
        Card(
            colors =
                CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f),
                    contentColor = Color.White,
                ),
        ) {
            Icon(
                imageVector = Icons.Default.PhoneAndroid,
                contentDescription = "",
                tint = colorResource(R.color.primary),
                modifier =
                    Modifier
                        .padding(10.dp)
                        .height(100.dp)
                        .width(100.dp),
            )
        }
        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text(
                text = device.modelNumber,
                style =
                    TextStyle(
                        fontFamily = FontFamily(Font(R.font.opensans_bold)),
                        fontSize = 26.sp,
                        color = colorResource(id = R.color.white),
                    ),
            )

            Spacer(Modifier.height(2.dp))
            Text(
                text = "Owner : ${device.costumerName}",
                style =
                    TextStyle(
                        fontFamily = FontFamily(Font(R.font.opensans_medium)),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.white),
                    ),
            )

            Text(
                text = "Phone : 9910000163",
                style =
                    TextStyle(
                        fontFamily = FontFamily(Font(R.font.opensans_medium)),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.white),
                    ),
            )
        }
    }
}

@Composable
fun ActionsButtonGrid(
    list: List<DeviceActionData>,
    onActionClick: (Actions) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
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
    val action: Actions,
    val actionName: String,
)

@Composable
fun DeviceActionButton(
    deviceActionData: DeviceActionData,
    modifier: Modifier = Modifier,
    onActionClick: (Actions) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
            modifier
                .clickable { onActionClick(deviceActionData.action) }
                .padding(4.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            colors =
                CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f),
                    contentColor = Color.Green,
                ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier =
                    Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
            ) {
                Card(
                    colors =
                        CardDefaults.cardColors(
                            containerColor = Color.Green.copy(alpha = 0.1f),
                            contentColor = Color.Green,
                        ),
                ) {
                    Box(Modifier.padding(10.dp)) {
                        Icon(
                            imageVector = deviceActionData.icon,
                            contentDescription = "",
                            modifier = Modifier.size(35.dp),
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    text = deviceActionData.actionName,
                    textAlign = TextAlign.Center,
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontSize = 13.sp,
                            color = colorResource(id = R.color.white),
                        ),
                )
            }
        }
    }
}

val deviceActionDataList =
    Actions.values().map { action ->
        when (action) {
            Actions.ACTION_LOCK_DEVICE -> DeviceActionData(Icons.Default.Lock, action, "Lock")
            Actions.ACTION_UNLOCK_DEVICE ->
                DeviceActionData(
                    Icons.Default.LockOpen,
                    action,
                    "Unlock",
                )

            Actions.ACTION_EMI_AUDIO_REMINDER ->
                DeviceActionData(
                    Icons.Default.Notifications,
                    action,
                    "EMI Audio Reminder",
                )

            Actions.ACTION_EMI_SCREEN_REMINDER ->
                DeviceActionData(
                    Icons.Default.ScreenLockRotation,
                    action,
                    "EMI Screen Reminder",
                )

            Actions.ACTION_GET_PHONE_NUMBER ->
                DeviceActionData(
                    Icons.Default.Phone,
                    action,
                    "Get Phone Number",
                )

            Actions.ACTION_GET_CONTACTS ->
                DeviceActionData(
                    Icons.Default.Contacts,
                    action,
                    "Get Contacts",
                )

            Actions.ACTION_GET_CONTACTS_VIA_MESSAGE ->
                DeviceActionData(
                    Icons.Default.Message,
                    action,
                    "Get Contacts via Message",
                )

//            Actions.ACTION_OFFLINE_LOCK_UNLOCK ->
//                DeviceActionData(
//                    Icons.Default.OfflineBolt,
//                    action,
//                    "Offline Lock/Unlock",
//                )

            Actions.ACTION_APP_UNLOCK ->
                DeviceActionData(
                    Icons.Default.VpnKey,
                    action,
                    "App Unlock",
                )

            Actions.ACTION_CAMERA_LOCK ->
                DeviceActionData(
                    Icons.Default.Camera,
                    action,
                    "Camera Lock",
                )

            Actions.ACTION_CAMERA_UNLOCK ->
                DeviceActionData(
                    Icons.Default.CameraAlt,
                    action,
                    "Camera Unlock",
                )

            Actions.ACTION_SET_WALLPAPER ->
                DeviceActionData(
                    Icons.Default.Wallpaper,
                    action,
                    "Set Wallpaper",
                )

            Actions.ACTION_REMOVE_WALLPAPER ->
                DeviceActionData(
                    Icons.Default.Wallpaper,
                    action,
                    "Remove Wallpaper",
                )

            Actions.ACTION_GET_LOCATION ->
                DeviceActionData(
                    Icons.Default.LocationOn,
                    action,
                    "Get Location",
                )

            Actions.ACTION_GET_LOCATION_VIA_MESSAGE ->
                DeviceActionData(
                    Icons.Default.Message,
                    action,
                    "Get Location via Message",
                )

            Actions.ACTION_REBOOT_DEVICE ->
                DeviceActionData(
                    Icons.Default.RestartAlt,
                    action,
                    "Reboot Device",
                )

            Actions.ACTION_CALL_LOCK -> DeviceActionData(Icons.Default.Call, action, "Call Lock")
            Actions.ACTION_CALL_UNLOCK ->
                DeviceActionData(
                    Icons.Default.CallEnd,
                    action,
                    "Call Unlock",
                )

            Actions.ACTION_RESET_PASSWORD ->
                DeviceActionData(
                    Icons.Default.VpnKey,
                    action,
                    "Reset Password",
                )

//            Actions.ACTION_REACTIVATE_DEVICE ->
//                DeviceActionData(
//                    Icons.Default.Sync,
//                    action,
//                    "Reactivate Device",
//                )
//
//            Actions.ACTION_DEACTIVATE_DEVICE ->
//                DeviceActionData(
//                    Icons.Default.Block,
//                    action,
//                    "Deactivate Device",
//                )

            Actions.ACTION_GET_DEVICE_INFO ->
                DeviceActionData(
                    Icons.Default.Info,
                    action,
                    "Get Device Info",
                )

            Actions.ACTION_GET_UNLOCK_CODE ->
                DeviceActionData(
                    Icons.Default.LockOpen,
                    action,
                    "Get Unlock Code",
                )

            Actions.ACTION_REMOVE_DEVICE ->
                DeviceActionData(
                    Icons.Default.Delete,
                    action,
                    "Remove Device",
                )

            Actions.ACTION_OFFLINE_LOCK ->
                DeviceActionData(
                    Icons.Default.Delete,
                    action,
                    "Offline Device",
                )

            Actions.ACTION_OFFLINE_UNLOCK ->
                DeviceActionData(
                    Icons.Default.Delete,
                    action,
                    "Offline unlock",
                )

            Actions.ACTION_LOCK_SCREEN ->
                DeviceActionData(
                    Icons.Default.Delete,
                    action,
                    "Lock screen",
                )

            Actions.ACTION_REG_DEVICE ->
                DeviceActionData(
                    Icons.Default.Delete,
                    action,
                    "Lock screen",
                )

            else -> {
                DeviceActionData(
                    Icons.Default.Delete,
                    action,
                    "Lock screen",
                )
            }
        }
    }

@Preview(name = "DeviceDetailsPreview", showBackground = true, showSystemUi = true)
@Composable
fun DeviceDetailsPreview() {
//    DeviceDetails(Device(deviceId = "OPPO VIVO", customerName = "Rahul"))
}
