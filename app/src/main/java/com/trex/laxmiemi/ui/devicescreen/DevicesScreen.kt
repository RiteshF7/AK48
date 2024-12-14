package com.trex.laxmiemi.ui.devicescreen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.trex.laxmiemi.R
import com.trex.laxmiemi.handlers.ShopActionExecutor
import com.trex.laxmiemi.ui.devicedetailsscreen.DeviceDetailActivity
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.Actions
import com.trex.rexnetwork.data.NewDevice

@Composable
fun DevicesScreen(viewModel: DevicesViewModel) {
    val viewState by viewModel.viewState.observeAsState(initial = DevicesViewState.Loading)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black.copy(alpha = 0.85f),
    ) {
        when (viewState) {
            is DevicesViewState.Loading -> LoadingView()
            is DevicesViewState.Success ->
                DeviceList(
                    devices = (viewState as DevicesViewState.Success).devices,
                    viewModel = viewModel,
                )

            is DevicesViewState.Error ->
                ErrorView(
                    message = (viewState as DevicesViewState.Error).message,
                )
        }
    }
}

@Composable
private fun DeviceList(
    devices: List<DeviceWithEMIStatus>,
    viewModel: DevicesViewModel,
) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        items(devices) { device ->
            DeviceCard(
                deviceWithStatus = device,
                onMarkPaid = { viewModel.markEmiAsPaid(device) },
                onDelete = { viewModel.deleteDevice(device.device) },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun DeviceCard(
    deviceWithStatus: DeviceWithEMIStatus,
    onMarkPaid: () -> Unit,
    onDelete: () -> Unit,
) {
    val context = LocalContext.current
    val device = deviceWithStatus.device
    val emiStatus = deviceWithStatus.emiStatus

    Card(
        onClick = {
            DeviceDetailActivity.go(context, deviceWithStatus.device)
        },
        colors =
            CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.1f),
                contentColor = Color.White,
            ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {
            // Device Info
            Text(
                text = device.costumerName,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = device.modelNumber,
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // EMI Status
            EMIStatusSection(emiStatus, device)

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            ActionButtons(
                deviceWithStatus = deviceWithStatus,
                onMarkPaid = onMarkPaid,
                onDelete = onDelete,
                onLockToggle = {
                    val action =
                        if (device.isLocked) {
                            Actions.ACTION_UNLOCK_DEVICE
                        } else {
                            Actions.ACTION_LOCK_DEVICE
                        }
                    if (emiStatus.isCompleted && action == Actions.ACTION_LOCK_DEVICE) {
                        Toast
                            .makeText(
                                context,
                                "Cannot lock device after EMIs are completed!",
                                Toast.LENGTH_SHORT,
                            ).show()
                    } else {
                        ShopActionExecutor(context).sendActionToClient(
                            ActionMessageDTO(device.fcmToken, action),
                        )
                    }
                },
            )
        }
    }
}

@Composable
private fun EMIStatusSection(
    emiStatus: EMIStatus,
    device: NewDevice,
) {
    val statusColor =
        when {
            emiStatus.isCompleted -> colorResource(R.color.primary)
            emiStatus.isDelayed -> colorResource(R.color.red_300)
            else -> colorResource(R.color.primary)
        }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Icon(
            imageVector =
                when {
                    emiStatus.isDelayed -> Icons.Default.Error
                    emiStatus.isCompleted -> Icons.Default.CheckCircle
                    else -> Icons.Default.Schedule
                },
            contentDescription = null,
            tint = statusColor,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text =
                when {
                    emiStatus.isCompleted -> "All EMIs are Completed"
                    emiStatus.isDelayed -> "EMI Delayed by ${emiStatus.delayInDays} days"
                    else -> "EMI Paid\nNext due: ${device.dueDate}"
                },
            color = statusColor,
        )
    }
}

@Composable
private fun ActionButtons(
    deviceWithStatus: DeviceWithEMIStatus,
    onMarkPaid: () -> Unit,
    onDelete: () -> Unit,
    onLockToggle: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        // Lock/Unlock Button
        Button(
            modifier = Modifier.weight(1f).padding(end = 10.dp),
            onClick = onLockToggle,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor =
                        if (deviceWithStatus.isDeviceLocked) {
                            colorResource(R.color.primary)
                        } else {
                            colorResource(R.color.red_300)
                        },
                ),
        ) {
            Icon(
                imageVector =
                    if (deviceWithStatus.isDeviceLocked) {
                        Icons.Default.Lock
                    } else {
                        Icons.Default.LockOpen
                    },
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text =
                    if (deviceWithStatus.isDeviceLocked) {
                        "Unlock"
                    } else {
                        "Lock"
                    },
            )
        }

        // Show appropriate action button based on status
        when {
            deviceWithStatus.emiStatus.isCompleted -> {
                Button(
                    modifier = Modifier.weight(1f).padding(end = 10.dp),
                    onClick = onDelete,
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.red_300),
                        ),
                    enabled = !deviceWithStatus.isDeviceLocked,
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Delete")
                }
            }

            deviceWithStatus.emiStatus.isDelayed -> {
                Button(
                    modifier = Modifier.weight(1f).padding(start = 10.dp),
                    onClick = onMarkPaid,
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.primary),
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Payment,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Mark Paid")
                }
            }
        }
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorView(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = message,
            color = colorResource(R.color.red_300),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
