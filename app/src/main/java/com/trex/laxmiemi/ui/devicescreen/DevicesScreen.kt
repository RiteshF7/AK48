package com.trex.laxmiemi.ui.devicescreen

import NewDeviceIds
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R
import com.trex.laxmiemi.handlers.ShopActionExecutor
import com.trex.laxmiemi.ui.components.TitleText
import com.trex.laxmiemi.ui.devicedetailsscreen.DeviceDetailActivity
import com.trex.laxmiemi.ui.qrcodescreen.ScanQrActivity
import com.trex.laxmiemi.ui.tokenbalancescreen.TokenBalanceActivity
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.Actions
import com.trex.rexnetwork.domain.firebasecore.firesstore.ShopFirestore
import com.trex.rexnetwork.utils.SharedPreferenceManager
import com.trex.rexnetwork.utils.startMyActivity

@Composable
fun DeviceScreen(viewModel: DevicesViewModel) {
    val viewState by viewModel.viewState.observeAsState(initial = DevicesViewState.Loading)

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.85f)),
    ) {
        when (viewState) {
            is DevicesViewState.Loading -> {
                LoadingScreen()
            }

            is DevicesViewState.Success -> {
                val devices = (viewState as DevicesViewState.Success).devices
                DeviceList(devices, viewModel)
            }

            is DevicesViewState.Error -> {
                val message = (viewState as DevicesViewState.Error).message
                ErrorScreen(message)
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Loading...",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = message,
            color = Color.Red,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun DeviceList(
    devices: List<DeviceWithEMIStatus>,
    viewModel: DevicesViewModel,
) {
    if (devices.isEmpty()) {
        NoDevicesScreen()
    } else {
        LazyColumn(
            modifier =
                Modifier
                    .padding(top = 10.dp)
                    .widthIn(max = 500.dp),
        ) {
            items(devices) { deviceWithEMIStatus ->
                DeviceListItem(deviceWithEMIStatus, viewModel)
            }
        }
    }
}

@Composable
fun DeviceListItem(
    deviceWithEMIStatus: DeviceWithEMIStatus,
    viewModel: DevicesViewModel,
) {
    val context = LocalContext.current
    val device = deviceWithEMIStatus.device

    Card(
        onClick = {
            DeviceDetailActivity.go(context, device)
        },
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.1f),
                contentColor = Color.White,
            ),
    ) {
        DeviceItemLayout(myDevice = deviceWithEMIStatus, viewModel)
    }
}

@Composable
fun NoDevicesScreen() {
    val context = LocalContext.current
    Scaffold { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = Color.Black.copy(alpha = 0.85f))
                    .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.not_found), // Add your logo resource
                contentDescription = "Shop Logo",
                modifier =
                    Modifier
                        .size(120.dp)
                        .padding(bottom = 24.dp),
            )

            // No devices text
            Text(
                text = "No Devices Added",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
            )

            Text(
                text = "Please add devices to get started",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 32.dp),
            )

            // Add device button
            Button(
                onClick = {
                    SharedPreferenceManager(context).getShopId()?.let { shopId ->
                        ShopFirestore().getTokenBalanceList(shopId) { tokenBalanceList ->
                            if (tokenBalanceList.isEmpty()) {
                                context.startMyActivity(TokenBalanceActivity::class.java)
                                Toast
                                    .makeText(
                                        context,
                                        "Low token balance!\n PLease buy token to proceed!",
                                        Toast.LENGTH_LONG,
                                    ).show()
                            } else {
                                val newDeviceIds =
                                    NewDeviceIds(shopId, tokenBalanceList.first())
                                context.startMyActivity<NewDeviceIds>(
                                    ScanQrActivity::class.java,
                                    newDeviceIds,
                                    false,
                                )
                            }
                        }
                    }
                },
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.primary),
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 32.dp),
            ) {
                Text(
                    text = "Add Device",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
fun DeviceItemLayout(
    myDevice: DeviceWithEMIStatus,
    viewModel: DevicesViewModel,
) {
    val context = LocalContext.current
    val device = myDevice.device

    Column {
        Row(
            modifier =
                Modifier
                    .padding(horizontal = 10.dp, vertical = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(
                shape = RoundedCornerShape(80.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            if (myDevice.emiStatus.isCompleted) {
                                colorResource(R.color.primary)
                            } else if (myDevice.emiStatus.isDelayed) {
                                colorResource(R.color.red_300)
                            } else {
                                colorResource(
                                    R.color.primary,
                                )
                            },
                        contentColor = Color.White,
                    ),
            ) {
                Box(Modifier.padding(10.dp)) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "",
                        tint = Color.White,
                        modifier =
                            Modifier
                                .size(40.dp)
                                .padding(5.dp),
                    )
                }
            }

            Column(
                modifier = Modifier.padding(start = 20.dp, top = 6.dp),
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    text = device.costumerName,
                    textAlign = TextAlign.Center,
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.opensans_medium)),
                            fontSize = 20.sp,
                            color = Color.White,
                        ),
                )
                Spacer(Modifier.height(5.dp))

                Text(
                    text = device.modelNumber,
                    textAlign = TextAlign.Center,
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.opensans_regular)),
                            fontSize = 12.sp,
                            color = Color.White,
                        ),
                )

                Spacer(Modifier.height(5.dp))
                EmiDetailsText(myDevice)
            }
        }
        DeviceFooter(myDevice, context, viewModel)
    }
}

@Composable
fun DeviceFooter(
    device: DeviceWithEMIStatus,
    context: Context,
    viewModel: DevicesViewModel,
) {
    if (device.emiStatus.isCompleted) {
        Box(
            modifier =
                Modifier
                    .clickable {
                        viewModel.deleteDevice(device.device)
                    }.background(color = colorResource(R.color.primary)),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().height(50.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close), // Replace with your icon resource
                    contentDescription = "Paid Icon",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp), // Adjust icon size as needed
                )
                Spacer(modifier = Modifier.width(10.dp)) // Space between icon and text
                Text(
                    text = "Remove device",
                    fontWeight = FontWeight.Bold,
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.opensans_medium)),
                            fontSize = 20.sp,
                            color = Color.White,
                        ),
                )
            }
        }
        return
    }
    Row {
        Box(
            modifier =
                Modifier
                    .height(50.dp)
                    .weight(1f)
                    .clickable {
                        if (device.emiStatus.isDelayed) {
                            viewModel.markEmiAsPaid(device.device)
                        } else {
                            ShopActionExecutor(context).sendActionToClient(
                                ActionMessageDTO(device.device.fcmToken, Actions.ACTION_UNLOCK_DEVICE),
                            )
                        }
                    }.background(color = colorResource(R.color.primary)),
            contentAlignment = Alignment.Center, // Centers content inside the Box
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.shield), // Replace with your icon resource
                    contentDescription = "Paid Icon",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp), // Adjust icon size as needed
                )
                Spacer(modifier = Modifier.width(4.dp)) // Space between icon and text
                Text(
                    text = if (device.emiStatus.isDelayed) "Mark as Paid" else "Unlock device",
                    textAlign = TextAlign.Center,
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.opensans_medium)),
                            fontSize = 16.sp,
                            color = Color.White,
                        ),
                )
            }
        }
        Box(
            modifier =
                Modifier
                    .height(50.dp)
                    .weight(1f)
                    .clickable {
                        ShopActionExecutor(context).sendActionToClient(
                            ActionMessageDTO(device.device.fcmToken, Actions.ACTION_LOCK_DEVICE),
                        )
                    }.background(color = colorResource(R.color.red_300)),
            contentAlignment = Alignment.Center, // Centers content inside the Box
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Paid Icon",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Lock Device",
                    textAlign = TextAlign.Center,
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.opensans_medium)),
                            fontSize = 16.sp,
                            color = Color.White,
                        ),
                )
            }
        }
    }
}

@Composable
fun EmiDetailsText(device: DeviceWithEMIStatus) {
    var emiStatus = ""
    if (device.emiStatus.isDelayed) {
        emiStatus = "${device.device.dueDate} EMI delay by ${device.emiStatus.delayInDays} days."
    } else {
        emiStatus = "${device.device.dueDate} EMI paid for this month"
    }
    if (device.emiStatus.isCompleted) {
        emiStatus = "EMI for this device is completed!"
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.CalendarMonth,
            contentDescription = "",
            tint =
                if (device.emiStatus.isDelayed) {
                    colorResource(R.color.red_300)
                } else {
                    colorResource(
                        R.color.primary,
                    )
                },
            modifier =
                Modifier
                    .height(15.dp)
                    .width(15.dp),
        )
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = emiStatus,
            textAlign = TextAlign.Center,
            style =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.opensans_bold)),
                    fontSize = 12.sp,
                    color =
                        if (device.emiStatus.isDelayed) {
                            colorResource(R.color.red_300)
                        } else {
                            colorResource(
                                R.color.primary,
                            )
                        },
                ),
        )
        Spacer(Modifier.width(5.dp))
        Icon(
            imageVector =
                if (device.emiStatus.isDelayed) {
                    Icons.Filled.Error
                } else {
                    Icons.Filled.Check
                },
            contentDescription = "",
            tint =
                if (device.emiStatus.isDelayed) {
                    colorResource(R.color.red_300)
                } else {
                    colorResource(
                        R.color.primary,
                    )
                },
            modifier =
                Modifier
                    .height(20.dp)
                    .width(20.dp),
        )
    }
}

@Composable
fun DeviceInformation(list: List<Pair<String, String>>) {
    Column(modifier = Modifier.padding(5.dp)) {
        list.forEach {
            DeviceInfoItem(key = it.first, value = it.second)
        }
    }
}

@Composable
fun DeviceInfoItem(
    key: String,
    value: String,
) {
    Column {
        Row(
            modifier =
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
        ) {
            Text(
                text = key + "   :",
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f),
                color = colorResource(id = R.color.grey_600),
            )
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                color = colorResource(id = R.color.grey_600),
            )
        }
        Divider(
            color = colorResource(id = R.color.grey_200),
            thickness = 0.2.dp,
        )
    }
}

@Composable
fun DeviceItemHeader(headerTitle: String) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(colorResource(id = R.color.blue_600)),
    ) {
        TitleText(
            text = headerTitle,
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(id = R.color.white),
        )
    }
}

@Composable
fun DeviceItemFooter() {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
    ) {
        Button(
            modifier =
                Modifier
                    .weight(1f)
                    .height(50.dp),
            onClick = {},
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.red_400)),
        ) {
            Icon(Icons.Default.Lock, contentDescription = "Lock")
        }

        Button(
            modifier =
                Modifier
                    .weight(1f)
                    .height(50.dp),
            onClick = {},
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(Color.Green),
        ) {
            Icon(Icons.Default.Clear, contentDescription = "Lock")
        }
    }
}

@Preview(name = "DeviceScreenPreview", showBackground = true)
@Composable
fun DeviceScreenPreview() {
//    DeviceListItem()
}
