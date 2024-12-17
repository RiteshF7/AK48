package com.trex.laxmiemi.ui.devicedetailsscreen

import android.widget.Toast
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CastConnected
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MapsHomeWork
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.ScreenLockRotation
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R
import com.trex.laxmiemi.ui.createdevicescreen.EditDeviceInfoActivity
import com.trex.rexnetwork.data.Actions
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.utils.SharedPreferenceManager

/*

*/
@Composable
fun DeviceDetails(
    device: NewDevice,
    vm: DeviceScreenDetailViewModel,
    onActionClick: (Actions) -> Unit,
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            DeviceDetailHeader(device)
            HorizontalDivider(Modifier.height(1.dp))
            Card(
                shape = RoundedCornerShape(0.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.1f),
                        contentColor = Color.White,
                    ),
            ) {
                Column(Modifier.padding(vertical = 10.dp, horizontal = 10.dp)) {
                    HeaderButtons(vm, device.deviceId)
                    QuickActionButton(
                        "Get device info",
                        Modifier
                            .padding(5.dp)
                            .background(
                                color = colorResource(R.color.primary),
                                shape = RoundedCornerShape(5.dp),
                            ),
                        Icons.Default.Devices,
                    ) {
                        EditDeviceInfoActivity.go(context, device.deviceId)
                    }
                    val lockIcon: ImageVector
                    val lockBackColor: Color
                    val lockText: String
                    val action: Actions

                    when {
                        device.deviceLockStatus -> {
                            lockIcon = Icons.Default.LockOpen
                            lockBackColor = colorResource(R.color.primary)
                            lockText = "Unlock device"
                            action = Actions.ACTION_UNLOCK_DEVICE
                        }

                        else -> {
                            lockIcon = Icons.Default.Lock
                            lockBackColor = colorResource(R.color.red_300)
                            lockText = "Lock device"
                            action = Actions.ACTION_LOCK_DEVICE
                        }
                    }

                    QuickActionButton(
                        lockText,
                        Modifier
                            .padding(5.dp)
                            .background(lockBackColor, shape = RoundedCornerShape(5.dp)),
                        lockIcon,
                    ) {
                        onActionClick(action)
                    }
                }
            }
        }
        Box(
            modifier =
                Modifier
                    .weight(1f) // Takes available space while respecting other elements
                    .fillMaxWidth(),
        ) {
            ActionsButtonGrid(list = deviceActionDataList, onActionClick)
        }
        // Bottom section
        Spacer(Modifier.padding(bottom = 10.dp, top = 0.dp))
        HorizontalDivider()

        DeleteDeviceButton(device, vm, onActionClick)
    }
}

@Composable
fun HeaderButtons(
    vm: DeviceScreenDetailViewModel,
    deviceId: String,
) {
    val context = LocalContext.current
    val codeText by vm.unlockCode
    QuickActionButton(
        codeText,
        Modifier
            .padding(5.dp)
            .background(color = colorResource(R.color.primary), shape = RoundedCornerShape(5.dp)),
        Icons.Default.Password,
    ) {
        SharedPreferenceManager(context).getShopId()?.let { shopId ->
            vm.generateUnlockCode(shopId, deviceId = deviceId)
        }
    }
}

@Composable
fun DeleteDeviceButton(
    device: NewDevice,
    vm: DeviceScreenDetailViewModel,
    onActionClick: (Actions) -> Unit,
) {
    val context = LocalContext.current
    var showConfirmationDialog by remember { mutableStateOf(false) }

    Box(
        modifier =
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(45.dp)
                .height(40.dp)
                .clickable { showConfirmationDialog = true }
                .background(
                    color = colorResource(R.color.red_300),
                    shape = RoundedCornerShape(5.dp),
                ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                tint = Color.White,
                contentDescription = "Delete device",
                modifier = Modifier.size(15.dp),
            )
            Spacer(Modifier.padding(6.dp))
            Text(
                text = "REMOVE DEVICE",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        }
    }

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = {
                Text(
                    "Confirm Device Removal",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Text(
                    "Are you sure you want to remove this device? This action cannot be undone.",
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        Toast
                            .makeText(context, "Device will be deleted soon!", Toast.LENGTH_SHORT)
                            .show()
                        vm.deleteDevice(device, onActionClick)
                        showConfirmationDialog = false
                    },
                    colors =
                        ButtonDefaults.textButtonColors(
                            contentColor = colorResource(R.color.red_300),
                        ),
                ) {
                    Text(
                        "Remove",
                        fontWeight = FontWeight.Bold,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmationDialog = false },
                ) {
                    Text("Cancel")
                }
            },
            shape = RoundedCornerShape(8.dp),
            containerColor = Color.White,
            tonalElevation = 8.dp,
        )
    }
}

@Composable
fun QuickActionButton(
    text: String,
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(45.dp)
                .height(40.dp)
                .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                tint = Color.White,
                contentDescription = "",
                modifier = Modifier.size(15.dp),
            )
            Spacer(Modifier.padding(6.dp))
            Text(
                text = text,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun DeviceDetailHeader(device: NewDevice) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    colorResource(R.color.primary),
                                    Color.Transparent,
                                ),
                        ),
                ).padding(15.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp, horizontal = 15.dp),
        ) {
            Card(
                colors =
                    CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.3f),
                        contentColor = Color.White,
                    ),
                shape = CircleShape,
                modifier = Modifier.size(60.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.MapsHomeWork,
                    contentDescription = "Device Icon",
                    tint = colorResource(R.color.primary),
                    modifier =
                        Modifier
                            .padding(15.dp)
                            .size(30.dp),
                )
            }
            Column(
                modifier = Modifier.padding(start = 20.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = device.modelNumber,
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.opensans_bold)),
                            fontSize = 26.sp,
                            color = Color.White,
                        ),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = device.costumerName, // Replace with actual device name if available
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.opensans_medium)),
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.7f),
                        ),
                )
            }
        }
    }
}

@Composable
fun ActionsButtonGrid(
    list: List<DeviceActionData>,
    onActionClick: (Actions) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
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
            modifier =
                Modifier
                    .fillMaxSize()
                    .heightIn(150.dp)
                    .widthIn(150.dp),
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
    listOf(
        DeviceActionData(
            Icons.Default.Lock,
            Actions.ACTION_LOCK_DEVICE,
            "Lock Device",
        ),
        DeviceActionData(
            Icons.Default.LockOpen,
            Actions.ACTION_UNLOCK_DEVICE,
            "Unlock device",
        ),
        DeviceActionData(
            Icons.Default.LocationOn,
            Actions.ACTION_GET_LOCATION,
            "Get Location",
        ),
        DeviceActionData(
            Icons.Default.Contacts,
            Actions.ACTION_GET_CONTACTS,
            "Get Contacts",
        ),
        DeviceActionData(
            Icons.Default.CallEnd,
            Actions.ACTION_CALL_LOCK,
            "Lock call",
        ),
        DeviceActionData(
            Icons.Default.CallEnd,
            Actions.ACTION_CALL_UNLOCK,
            "Unlock call",
        ),
        DeviceActionData(
            Icons.Default.ScreenLockRotation,
            Actions.ACTION_EMI_SCREEN_REMINDER,
            "Screen Reminder",
        ),
        DeviceActionData(
            Icons.Default.Notifications,
            Actions.ACTION_EMI_AUDIO_REMINDER,
            "EMI Audio Reminder",
        ),
        DeviceActionData(
            Icons.Default.Camera,
            Actions.ACTION_CAMERA_LOCK,
            "Camera Lock",
        ),
        DeviceActionData(
            Icons.Default.CameraAlt,
            Actions.ACTION_CAMERA_UNLOCK,
            "Unlock camera",
        ),
        DeviceActionData(
            Icons.Default.Wallpaper,
            Actions.ACTION_REMOVE_WALLPAPER,
            "Remove Wallpaper",
        ),
        DeviceActionData(
            Icons.Default.RestartAlt,
            Actions.ACTION_REBOOT_DEVICE,
            "Reboot Device",
        ),
        DeviceActionData(
            Icons.Default.VpnKey,
            Actions.ACTION_RESET_PASSWORD,
            "Reset Password",
        ),
        DeviceActionData(
            Icons.Default.CastConnected,
            Actions.ACTION_TEST_MESSAGE,
            "Test connection",
        ),
    )

//    Actions.values().map { action ->
//        when (action) {
//            Actions.ACTION_LOCK_DEVICE ->
//                DeviceActionData(
//                    Icons.Default.Lock,
//                    action,
//                    "Lock",
//                )
//
//            Actions.ACTION_UNLOCK_DEVICE ->
//                DeviceActionData(
//                    Icons.Default.LockOpen,
//                    action,
//                    "Unlock",
//                )
//
//            Actions.ACTION_EMI_AUDIO_REMINDER ->
//                DeviceActionData(
//                    Icons.Default.Notifications,
//                    action,
//                    "EMI Audio Reminder",
//                )
//
//            Actions.ACTION_EMI_SCREEN_REMINDER ->
//                DeviceActionData(
//                    Icons.Default.ScreenLockRotation,
//                    action,
//                    "EMI Screen Reminder",
//                )
//
//            Actions.ACTION_GET_PHONE_NUMBER ->
//                DeviceActionData(
//                    Icons.Default.Phone,
//                    action,
//                    "Get Phone Number",
//                )
//
//            Actions.ACTION_GET_CONTACTS ->
//                DeviceActionData(
//                    Icons.Default.Contacts,
//                    action,
//                    "Get Contacts",
//                )
//
//            Actions.ACTION_GET_CONTACTS_VIA_MESSAGE ->
//                DeviceActionData(
//                    Icons.Default.Message,
//                    action,
//                    "Get Contacts via Message",
//                )
//
// //            Actions.ACTION_OFFLINE_LOCK_UNLOCK ->
// //                DeviceActionData(
// //                    Icons.Default.OfflineBolt,
// //                    action,
// //                    "Offline Lock/Unlock",
// //                )
//
//            Actions.ACTION_APP_UNLOCK ->
//                DeviceActionData(
//                    Icons.Default.VpnKey,
//                    action,
//                    "App Unlock",
//                )
//
//            Actions.ACTION_CAMERA_LOCK ->
//                DeviceActionData(
//                    Icons.Default.Camera,
//                    action,
//                    "Camera Lock",
//                )
//
//            Actions.ACTION_CAMERA_UNLOCK ->
//                DeviceActionData(
//                    Icons.Default.CameraAlt,
//                    action,
//                    "Camera Unlock",
//                )
//
//            Actions.ACTION_SET_WALLPAPER ->
//                DeviceActionData(
//                    Icons.Default.Wallpaper,
//                    action,
//                    "Set Wallpaper",
//                )
//
//            Actions.ACTION_REMOVE_WALLPAPER ->
//                DeviceActionData(
//                    Icons.Default.Wallpaper,
//                    action,
//                    "Remove Wallpaper",
//                )
//
//            Actions.ACTION_GET_LOCATION ->
//                DeviceActionData(
//                    Icons.Default.LocationOn,
//                    action,
//                    "Get Location",
//                )
//
//            Actions.ACTION_GET_LOCATION_VIA_MESSAGE ->
//                DeviceActionData(
//                    Icons.Default.Message,
//                    action,
//                    "Get Location via Message",
//                )
//
//            Actions.ACTION_REBOOT_DEVICE ->
//                DeviceActionData(
//                    Icons.Default.RestartAlt,
//                    action,
//                    "Reboot Device",
//                )
//
//            Actions.ACTION_CALL_LOCK -> DeviceActionData(Icons.Default.Call, action, "Call Lock")
//            Actions.ACTION_CALL_UNLOCK ->
//                DeviceActionData(
//                    Icons.Default.CallEnd,
//                    action,
//                    "Call Unlock",
//                )
//
//            Actions.ACTION_RESET_PASSWORD ->
//                DeviceActionData(
//                    Icons.Default.VpnKey,
//                    action,
//                    "Reset Password",
//                )
//
// //            Actions.ACTION_REACTIVATE_DEVICE ->
// //                DeviceActionData(
// //                    Icons.Default.Sync,
// //                    action,
// //                    "Reactivate Device",
// //                )
// //
// //            Actions.ACTION_DEACTIVATE_DEVICE ->
// //                DeviceActionData(
// //                    Icons.Default.Block,
// //                    action,
// //                    "Deactivate Device",
// //                )
//
//            Actions.ACTION_GET_DEVICE_INFO ->
//                DeviceActionData(
//                    Icons.Default.Info,
//                    action,
//                    "Get Device Info",
//                )
//
//            Actions.ACTION_GET_UNLOCK_CODE ->
//                DeviceActionData(
//                    Icons.Default.LockOpen,
//                    action,
//                    "Get Unlock Code",
//                )
//
//            Actions.ACTION_REMOVE_DEVICE ->
//                DeviceActionData(
//                    Icons.Default.Delete,
//                    action,
//                    "Remove Device",
//                )
//
//            Actions.ACTION_OFFLINE_LOCK ->
//                DeviceActionData(
//                    Icons.Default.Delete,
//                    action,
//                    "Offline Device",
//                )
//
//            Actions.ACTION_OFFLINE_UNLOCK ->
//                DeviceActionData(
//                    Icons.Default.Delete,
//                    action,
//                    "Offline unlock",
//                )
//
//            Actions.ACTION_LOCK_SCREEN ->
//                DeviceActionData(
//                    Icons.Default.Delete,
//                    action,
//                    "Lock screen",
//                )
//
//            Actions.ACTION_REG_DEVICE ->
//                DeviceActionData(
//                    Icons.Default.Delete,
//                    action,
//                    "Lock screen",
//                )
//
//            else -> {
//                DeviceActionData(
//                    Icons.Default.Delete,
//                    action,
//                    "Lock screen",
//                )
//            }
//        }
//    }

@Composable
fun ShowPhoneInfo(onClick: (() -> Unit)) {
    Card(shape = RoundedCornerShape(4.dp), modifier = Modifier.padding(20.dp)) {
        Box(
            Modifier
                .background(color = colorResource(R.color.primary))
                .padding(10.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    modifier =
                        Modifier
                            .size(20.dp)
                            .padding(end = 4.dp),
                    imageVector = Icons.Default.PermDeviceInformation,
                    contentDescription = "",
                    tint = Color.White,
                )
                Text(
                    text = "Show Device information",
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.opensans_bold)),
                            fontSize = 18.sp,
                            color = colorResource(id = R.color.white),
                        ),
                )
            }
        }
    }
}

@Preview(name = "DeviceDetailsPreview", showBackground = true, showSystemUi = false)
@Composable
fun DeviceDetailsPreview() {
    ShowPhoneInfo { }
}
