package com.trex.laxmiemi.ui.devicesscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trex.laxmiemi.R
import com.trex.laxmiemi.firebase.firestore.Device
import com.trex.laxmiemi.ui.components.TitleText

@Composable
fun DummyDeviceList(): List<Device> {
    return remember {
        listOf(
            Device(
                deviceId = "12345",
                imei = "1111222233334444",
                customerName = "John Doe",
                emiStatus = "Paid",
                lastPaymentDate = "2024-09-25",
                dateTimeSold = "2024-01-10",
                status = "Active",
                tokenId = "token123",
                emiDueDate = "2024-10-25",
                lockStatus = "Unlocked",
                lastCommunication = System.currentTimeMillis()
            ),
            Device(
                deviceId = "67890",
                imei = "5555666677778888",
                customerName = "Jane Smith",
                emiStatus = "Due",
                lastPaymentDate = "2024-08-15",
                dateTimeSold = "2024-02-05",
                status = "Active",
                tokenId = "token456",
                emiDueDate = "2024-09-25",
                lockStatus = "Locked",
                lastCommunication = System.currentTimeMillis()
            )
        )
    }
}


@Composable
fun DeviceScreen() {
    DeviceList(devices = DummyDeviceList())
}


@Composable
fun DeviceList(devices: List<Device>) {
    Box(Modifier.fillMaxWidth().fillMaxHeight()) {
        LazyColumn(modifier = Modifier
            .widthIn(max = 500.dp)
            .align(Alignment.Center)
        ){
            items(devices) { device ->
                DeviceListItem(device)
            }
        }
    }
}


@Composable
fun DeviceListItem(device: Device) {
    val deviceInformations = remember {
        listOf(
            Pair(device::deviceId.name, device.deviceId),
            Pair(device::imei.name, device.imei),
            Pair(device::customerName.name, device.customerName)
        )
    }

    Card(Modifier.padding(10.dp)) {
        Column {
            DeviceItemHeader(device.deviceId)
            DeviceInformation(deviceInformations)
            DeviceItemFooter()
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
fun DeviceInfoItem(key: String, value: String) {
    Column {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = key + "   :",
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f),
                color = colorResource(id = R.color.grey_600)
            )
            Text(
                text = value, fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                color = colorResource(id = R.color.grey_600)
            )
        }
        Divider(
            color = colorResource(id = R.color.grey_200),
            thickness = 0.2.dp
        )
    }
}


@Composable
fun DeviceItemHeader(headerTitle: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(colorResource(id = R.color.blue_600))
    ) {
        TitleText(
            text = headerTitle,
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(id = R.color.white)
        )
    }
}


@Composable
fun DeviceItemFooter() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            onClick = {}, shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.red_400))
        ) {
            Icon(Icons.Default.Lock, contentDescription = "Lock")
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            onClick = {}, shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(Color.Green)
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

