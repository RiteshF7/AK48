package com.trex.laxmiemi.ui.createdevicescreen

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.trex.laxmiemi.handlers.ShopActionExecutor
import com.trex.rexnetwork.Constants
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.Actions
import com.trex.rexnetwork.data.DeviceInfo
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeviceFirestore
import com.trex.rexnetwork.domain.firebasecore.firesstore.Shop
import com.trex.rexnetwork.domain.firebasecore.firesstore.ShopFirestore
import com.trex.rexnetwork.utils.SharedPreferenceManager
import com.trex.rexnetwork.utils.getExtraData
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormData(
    val costumerName: String = "",
    val costumerPhone: String = "",
    val emiPerMonth: String = "",
    val dueDate: String = "",
    val durationInMonths: String = "",
    val imeiOne: String = "",
    val imeiTwo: String = "",
    val deviceModel: String = "",
) : Parcelable

class CreateDeviceActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferenceManager
    private lateinit var newDevice: NewDevice
    private val shopRepo = ShopFirestore()
    private lateinit var messageDTO: ActionMessageDTO
    private var consumableToken = ""
    private var consumableTokenList = mutableListOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messageDTO = intent.getExtraData<ActionMessageDTO>()

        val deviceInfoPayload = messageDTO.payload[Actions.ACTION_REG_DEVICE.name]

        val deviceInfo = Gson().fromJson(deviceInfoPayload, DeviceInfo::class.java)

        val formData = FormData(deviceModel = deviceInfo.deviceModel)

        sharedPreferences = SharedPreferenceManager(this)
        newDevice = NewDevice()
        sharedPreferences.getShopId()?.let { shopId ->
            shopRepo.getTokenBalanceList(shopId) {
                consumableTokenList = it.toMutableList()
                consumableToken = it.first()
                newDevice.deviceId = consumableToken
            }
            newDevice.shopId = shopId
            newDevice.fcmToken = deviceInfo.fcmToken
        }
        setContent {
            MaterialTheme {
                DeviceFormScreen(
                    initialFormState = formData,
                    onFormSubmit = { data ->
                        newDevice.imeiOne = data.imeiOne
                        newDevice.imeiTwo = data.imeiTwo
                        newDevice.costumerName = data.costumerName
                        newDevice.costumerPhone = data.costumerPhone
                        newDevice.emiPerMonth = data.emiPerMonth
                        newDevice.dueDate = data.dueDate
                        newDevice.durationInMonths = data.durationInMonths
                        newDevice.modelNumber = data.deviceModel

                        handleFormSubmission(deviceInfo.fcmToken, this)
                    },
                )
            }
        }
    }

    private fun handleFormSubmission(
        fcmToken: String,
        context: Context,
    ) {
        val deviceRepo = DeviceFirestore(newDevice.shopId)
        deviceRepo.createOrUpdateDevice(newDevice.deviceId, newDevice, {
            removeTokenFromBalance()
            sendResponseAndFinish(true, fcmToken, context)
        }, {
            sendResponseAndFinish(false, fcmToken, context)
        })
    }

    private fun removeTokenFromBalance() {
        consumableTokenList.remove(consumableToken)
        shopRepo.updateSingleField(
            newDevice.shopId,
            Shop::tokenBalance.name,
            consumableTokenList,
            {
                Log.i("some", "removeTokenFromBalance: Token consumed!!")
            },
            {
                Log.e("TAG", "removeTokenFromBalance: error consuming token!! $it")
            },
        )
    }

    private fun sendResponseAndFinish(
        isSuccess: Boolean,
        fcmToken: String,
        context: Context,
    ) {
        val status = getStatus(isSuccess)
        val response = buildResponse(fcmToken, status)

        ShopActionExecutor(context).sendResponse(response)
        finish()
    }

    private fun buildResponse(
        fcmToken: String,
        status: String,
    ) = ActionMessageDTO(
        fcmToken = fcmToken,
        action = Actions.ACTION_REG_DEVICE,
        payload =
            mapOf(
                Constants.KEY_RESPOSE_RESULT_STATUS to status,
                Actions.ACTION_REG_DEVICE.name to "Device created successfully!!",
                NewDevice::deviceId.name to "${newDevice.deviceId}",
            ),
        requestId = messageDTO.requestId,
    )

    private fun getStatus(isSuccess: Boolean) =
        if (isSuccess) {
            Constants.RESPONSE_RESULT_SUCCESS
        } else {
            Constants.RESPONSE_RESULT_FAILED
        }
}

@Composable
fun DeviceFormScreen(
    initialFormState: FormData,
    onFormSubmit: (FormData) -> Unit,
) {
    val requiredFields = setOf("costumerName", "imeiOne", "deviceModel")

    var formState by remember {
        mutableStateOf(
            initialFormState,
        )
    }
    var errors by remember { mutableStateOf(mapOf<String, String>()) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Customer Name field
            item {
                FormField(
                    label = "Customer Name*",
                    value = formState.costumerName,
                    error = errors["costumerName"],
                    onValueChange = {
                        formState = formState.copy(costumerName = it)
                        errors = errors - "costumerName"
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Next,
                            capitalization = KeyboardCapitalization.Words,
                        ),
                )
            }

            // Customer Phone field
            item {
                FormField(
                    label = "Customer Phone",
                    value = formState.costumerPhone,
                    error = errors["costumerPhone"],
                    onValueChange = {
                        formState = formState.copy(costumerPhone = it)
                        errors = errors - "costumerPhone"
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next,
                        ),
                )
            }

            // EMI Per Month field
            item {
                FormField(
                    label = "EMI Per Month",
                    value = formState.emiPerMonth,
                    error = errors["emiPerMonth"],
                    onValueChange = {
                        formState = formState.copy(emiPerMonth = it)
                        errors = errors - "emiPerMonth"
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                )
            }

            // Due Date field
            item {
                FormField(
                    label = "Due Date",
                    value = formState.dueDate,
                    error = errors["dueDate"],
                    onValueChange = {
                        formState = formState.copy(dueDate = it)
                        errors = errors - "dueDate"
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                )
            }

            // Duration in Months field
            item {
                FormField(
                    label = "Duration (Months)",
                    value = formState.durationInMonths,
                    error = errors["durationInMonths"],
                    onValueChange = {
                        formState = formState.copy(durationInMonths = it)
                        errors = errors - "durationInMonths"
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                )
            }

            // IMEI One field
            item {
                FormField(
                    label = "IMEI 1*",
                    value = formState.imeiOne,
                    error = errors["imeiOne"],
                    onValueChange = {
                        formState = formState.copy(imeiOne = it)
                        errors = errors - "imeiOne"
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                )
            }

            // IMEI Two field
            item {
                FormField(
                    label = "IMEI 2",
                    value = formState.imeiTwo,
                    error = errors["imeiTwo"],
                    onValueChange = {
                        formState = formState.copy(imeiTwo = it)
                        errors = errors - "imeiTwo"
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                )
            }

            // Device Model field
            item {
                FormField(
                    label = "Device Model*",
                    value = formState.deviceModel,
                    error = errors["deviceModel"],
                    onValueChange = {
                        formState = formState.copy(deviceModel = it)
                        errors = errors - "deviceModel"
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Done,
                        ),
                )
            }
        }

        // Submit button
        Button(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            onClick = {
                if (validateForm(formState, requiredFields)) {
                    onFormSubmit(formState)
                } else {
                    errors = getFormErrors(formState, requiredFields)
                }
            },
        ) {
            Text("Submit")
        }
    }
}

@Composable
fun FormField(
    label: String,
    value: String,
    error: String?,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = error != null,
            keyboardOptions = keyboardOptions,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
            )
        }
    }
}

private fun validateForm(
    data: FormData,
    requiredFields: Set<String>,
): Boolean = getFormErrors(data, requiredFields).isEmpty()

private fun getFormErrors(
    data: FormData,
    requiredFields: Set<String>,
): Map<String, String> {
    val errors = mutableMapOf<String, String>()

    // Required field validation
    if (requiredFields.contains("costumerName") && data.costumerName.isBlank()) {
        errors["costumerName"] = "Customer name is required"
    }

    if (requiredFields.contains("costumerPhone") && data.costumerPhone.isBlank()) {
        errors["costumerPhone"] = "Customer phone is required"
    } else if (data.costumerPhone.isNotBlank() &&
        !android.util.Patterns.PHONE
            .matcher(data.costumerPhone)
            .matches()
    ) {
        errors["costumerPhone"] = "Invalid phone format"
    }

    if (data.emiPerMonth.isNotBlank() && data.emiPerMonth.toDoubleOrNull() == null) {
        errors["emiPerMonth"] = "EMI must be a valid number"
    }

    if (data.durationInMonths.isNotBlank() && data.durationInMonths.toIntOrNull() == null) {
        errors["durationInMonths"] = "Duration must be a valid number"
    }

    if (requiredFields.contains("imeiOne") && data.imeiOne.isBlank()) {
        errors["imeiOne"] = "IMEI 1 is required"
    } else if (data.imeiOne.isNotBlank() && !data.imeiOne.matches(Regex("^[0-9]{15}$"))) {
        errors["imeiOne"] = "Invalid IMEI format (should be 15 digits)"
    }

    if (data.imeiTwo.isNotBlank() && !data.imeiTwo.matches(Regex("^[0-9]{15}$"))) {
        errors["imeiTwo"] = "Invalid IMEI format (should be 15 digits)"
    }

    if (requiredFields.contains("deviceModel") && data.deviceModel.isBlank()) {
        errors["deviceModel"] = "Device model is required"
    }

    return errors
}
