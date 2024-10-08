package com.trex.laxmiemi.ui.devicescreen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trex.laxmiemi.R
import com.trex.laxmiemi.ui.components.TitleText
import com.trex.laxmiemi.ui.devicedetailsscreen.DeviceDetailActivity
import com.trex.laxmiemi.utils.CommonConstants.SINGLE_DEVICE_DATA
import com.trex.rexcommon.data.NewDevice

@Composable
fun DeviceScreen(vm: DevicesViewModel) {
    val devices = vm.devices.observeAsState(emptyList())
    DeviceList(devices.value)
}

@Composable
fun DeviceList(devices: List<NewDevice>) {
    val context = LocalContext.current

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable {},
    ) {
        LazyColumn(
            modifier =
                Modifier
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
    val localContext = LocalContext.current

    val deviceInformation =
        remember {
            listOf(
                Pair(device::fcmToken.name, device.fcmToken),
                Pair(device::imeiOne.name, device.imeiOne),
                Pair(device::costumerName.name, device.costumerName),
            )
        }

    Box(
        Modifier.clickable {
            val deviceDetailIntent = Intent(localContext, DeviceDetailActivity::class.java)
            deviceDetailIntent.apply {
                putExtra(SINGLE_DEVICE_DATA, device)
            }
            localContext.startActivity(deviceDetailIntent)
        },
    ) {
        Card(Modifier.padding(10.dp)) {
            Column {
                DeviceItemHeader(device.fcmToken)
                DeviceInformation(deviceInformation)
                DeviceItemFooter()
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
