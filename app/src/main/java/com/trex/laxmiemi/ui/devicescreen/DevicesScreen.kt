package com.trex.laxmiemi.ui.devicescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.trex.laxmiemi.ui.components.TitleText
import com.trex.laxmiemi.ui.devicedetailsscreen.DeviceDetailActivity
import com.trex.rexnetwork.data.NewDevice

data class DeviceListState(
    val devices: NewDevice,
    val isDeviceLocked: Boolean,
    val isEmiDelay: Boolean,
    val delayInDays: Int,
)

@Composable
fun DeviceScreen(vm: DevicesViewModel) {
    val devices = vm.devices.observeAsState(emptyList())
    DeviceList(devices.value)
}

@Composable
fun DeviceList(devices: List<NewDevice>) {
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black.copy(alpha = 0.85f)),
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .padding(top = 10.dp)
                    .widthIn(max = 500.dp)
                    .align(Alignment.TopCenter),
        ) {
            items(devices) { device ->
                DeviceListItem(device)
            }
        }
    }
}

@Composable
fun DeviceListItem(device: NewDevice) {
    val context = LocalContext.current

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
                            containerColor = Color.Red.copy(alpha = 0.1f),
                            contentColor = Color.White,
                        ),
                ) {
                    Box(Modifier.padding(10.dp)) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "",
                            tint = colorResource(R.color.red_300),
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
                        text = "Rahul yadav",
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = "",
                            tint = colorResource(R.color.red_300),
                            modifier =
                                Modifier
                                    .height(15.dp)
                                    .width(15.dp),
                        )
                        Text(
                            text = "12/12/2024 ( Delay by 4 days )",
                            textAlign = TextAlign.Center,
                            style =
                                TextStyle(
                                    fontFamily = FontFamily(Font(R.font.opensans_bold)),
                                    fontSize = 12.sp,
                                    color = colorResource(R.color.red_300),
                                ),
                        )
                        Spacer(Modifier.width(5.dp))
                        Icon(
                            imageVector = Icons.Filled.Error,
                            contentDescription = "",
                            tint = colorResource(R.color.red_300),
                            modifier =
                                Modifier
                                    .height(20.dp)
                                    .width(20.dp),
                        )
                    }
                }
            }

            Row {
                Box(
                    modifier =
                        Modifier
                            .height(50.dp)
                            .weight(1f)
                            .background(color = colorResource(R.color.primary)),
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
                            text = "EMI Paid",
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
                            .background(color = colorResource(R.color.red_300)),
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
