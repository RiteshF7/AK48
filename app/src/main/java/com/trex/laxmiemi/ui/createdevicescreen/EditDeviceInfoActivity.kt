package com.trex.laxmiemi.ui.createdevicescreen

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trex.laxmiemi.R
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeviceFirestore
import com.trex.rexnetwork.utils.SharedPreferenceManager
import com.trex.rexnetwork.utils.getExtraData
import com.trex.rexnetwork.utils.startMyActivity
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Locale

@Parcelize
data class FormData(
    val costumerName: String = "",
    val costumerEmail: String = "",
    val costumerLoanNumer: String = "",
    val costumerPhone: String = "",
    val emiPerMonth: String = "",
    val dueDate: String = "",
    val durationInMonths: String = "",
    val imeiOne: String = "",
    val imeiTwo: String = "",
    val deviceModel: String = "",
) : Parcelable

class EditDeviceInfoActivity : ComponentActivity() {
    companion object {
        fun go(
            context: Context,
            deviceId: String,
        ) {
            SharedPreferenceManager(context).getShopId()?.let { shopId ->
                DeviceFirestore(shopId).getSingleDevice(deviceId, { device ->
                    context.startMyActivity(EditDeviceInfoActivity::class.java, device, true)
                }, {})
            }
        }
    }

    private lateinit var sharedPreferences: SharedPreferenceManager
    private lateinit var newDevice: NewDevice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newDevice = intent.getExtraData<NewDevice>()

        val formData =
            FormData(
                costumerName = newDevice.costumerName,
                costumerEmail = newDevice.email,
                costumerLoanNumer = newDevice.loanNumber,
                costumerPhone = newDevice.costumerPhone,
                emiPerMonth = newDevice.emiPerMonth,
                dueDate = newDevice.dueDate,
                durationInMonths = newDevice.durationInMonths,
                imeiOne = newDevice.imeiOne,
                imeiTwo = newDevice.imeiTwo,
                deviceModel = newDevice.modelNumber,
            )

        sharedPreferences = SharedPreferenceManager(this)
        setContent {
            MaterialTheme {
                DeviceFormScreen(
                    initialFormState = formData,
                    onFormSubmit = { data ->
                        newDevice.costumerName = data.costumerName
                        newDevice.email = data.costumerEmail
                        newDevice.loanNumber = data.costumerLoanNumer
                        newDevice.costumerPhone = data.costumerPhone
                        newDevice.emiPerMonth = data.emiPerMonth
                        newDevice.dueDate = data.dueDate
                        newDevice.firstDueDate = data.dueDate
                        newDevice.durationInMonths = data.durationInMonths
                        newDevice.modelNumber = data.deviceModel
                        handleFormSubmission()
                    },
                )
            }
        }
    }

    private fun handleFormSubmission() {
        val deviceRepo = DeviceFirestore(newDevice.shopId)
        deviceRepo.createOrUpdateDevice(newDevice.deviceId, newDevice, {
            finish()
        }, {
            finish()
        })
    }
}

@Preview
@Composable
fun somePreview() {
    DeviceFormScreen(FormData()) { }
}

@Composable
fun DeviceFormScreen(
    initialFormState: FormData,
    onFormSubmit: (FormData) -> Unit,
) {
    val requiredFields =
        setOf("costumerName", "deviceModel", "costumerPhone", "email", "loanNumber")

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
                .background(Color.Black.copy(alpha = 0.85f))
                .padding(16.dp),
    ) {
        Text(
            text = "Update Details",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 14.dp),
        )

        HorizontalDivider(Modifier.padding(bottom = 14.dp))

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
                    label = "Customer Name",
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
            item {
                FormField(
                    label = "Customer Email",
                    value = formState.costumerEmail,
                    error = errors["email"],
                    onValueChange = {
                        formState = formState.copy(costumerEmail = it)
                        errors = errors - "email"
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Next,
                            capitalization = KeyboardCapitalization.Words,
                        ),
                )
            }
            item {
                FormField(
                    label = "Loan number",
                    value = formState.costumerLoanNumer,
                    error = errors["loanNumber"],
                    onValueChange = {
                        formState = formState.copy(costumerLoanNumer = it)
                        errors = errors - "loanNumber"
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Next,
                            capitalization = KeyboardCapitalization.Words,
                        ),
                )
            }

            item {
                FormField(
                    label = "Device Model",
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
            item {
                FormField(
                    label = "IMEI one",
                    value = formState.imeiOne,
                    error = errors["imeione"],
                    onValueChange = {
                        formState = formState.copy(imeiOne = it)
                        errors = errors - "imeione"
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next,
                        ),
                )
            }
            item {
                FormField(
                    label = "IMEI two",
                    value = formState.imeiTwo,
                    error = errors["imeitwo"],
                    onValueChange = {
                        formState = formState.copy(imeiTwo = it)
                        errors = errors - "imeitwo"
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next,
                        ),
                )
            }

//            item {
//                FormField(
//                    label = "EMI Per Month in ₹",
//                    value = formState.emiPerMonth,
//                    error = errors["emiPerMonth"],
//                    onValueChange = {
//                        formState = formState.copy(emiPerMonth = it)
//                        errors = errors - "emiPerMonth"
//                    },
//                    keyboardOptions =
//                        KeyboardOptions(
//                            keyboardType = KeyboardType.Number,
//                            imeAction = ImeAction.Next,
//                        ),
//                )
//            }
//
//            item {
//                FormField(
//                    label = "Enter First Due Date (dd-mm-yyyy)",
//                    value = formState.dueDate,
//                    error = errors["dueDate"],
//                    onValueChange = {
//                        formState = formState.copy(dueDate = it)
//                        errors = errors - "dueDate"
//                    },
//                    keyboardOptions =
//                        KeyboardOptions(
//                            keyboardType = KeyboardType.Number,
//                            imeAction = ImeAction.Next,
//                        ),
//                )
//            }
//
//            item {
//                FormField(
//                    label = "Duration (Months)",
//                    value = formState.durationInMonths,
//                    error = errors["durationInMonths"],
//                    onValueChange = {
//                        formState = formState.copy(durationInMonths = it)
//                        errors = errors - "durationInMonths"
//                    },
//                    keyboardOptions =
//                        KeyboardOptions(
//                            keyboardType = KeyboardType.Number,
//                            imeAction = ImeAction.Next,
//                        ),
//                )
//            }
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
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.primary)),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                "Submit",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
            )
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
        Card(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(12.dp),
                        ambientColor = Color.White.copy(alpha = 0.1f),
                    ),
            backgroundColor = Color(0xFF1E1E1E),
            shape = RoundedCornerShape(12.dp),
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                label = {
                    Text(
                        label,
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                isError = error != null,
                keyboardOptions = keyboardOptions,
                modifier =
                    Modifier
                        .fillMaxWidth(),
                colors =
                    TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                textStyle =
                    MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White,
                    ),
                singleLine = true,
            )
        }

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

fun isValidDate(date: String): Boolean {
    val regex = """\d{2}-\d{2}-\d{4}""".toRegex()
    if (!regex.matches(date)) {
        return false
    }
    return try {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        sdf.isLenient = false
        sdf.parse(date)
        true
    } catch (e: Exception) {
        false
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
    if (data.costumerName.isBlank()) {
        errors["costumerName"] = "Customer name is required"
    }

    if (data.costumerPhone.isBlank()) {
        errors["costumerPhone"] = "Customer phone is required"
    } else if (data.costumerPhone.isNotBlank() &&
        !android.util.Patterns.PHONE
            .matcher(data.costumerPhone)
            .matches()

    ) {
        errors["costumerPhone"] = "Invalid phone format"
    }

    if (requiredFields.contains("emiPerMonth") && data.emiPerMonth.isNotBlank() && data.emiPerMonth.toDoubleOrNull() == null) {
        errors["emiPerMonth"] = "EMI must be a valid number"
    }

    if (requiredFields.contains("durationInMonths") && data.durationInMonths.isNotBlank() && data.durationInMonths.toIntOrNull() == null) {
        errors["durationInMonths"] = "Duration must be a valid number"
    }

//    if (requiredFields.contains("dueDate") && data.dueDate.isBlank()) {
//        errors["dueDate"] = "Due date is required"
//    } else if (!isValidDate(data.dueDate)) {
//        errors["dueDate"] = "Invalid date format. Please provide date in dd-mm-yyyy format."
//    }

    if (data.imeiOne.isNotBlank() && !data.imeiOne.matches(Regex("^[0-9]{15}$"))) {
        errors["imeione"] = "Invalid IMEI format (should be 15 digits)"
    }

    if (data.imeiTwo.isNotBlank() && !data.imeiTwo.matches(Regex("^[0-9]{15}$"))) {
        errors["imeitwo"] = "Invalid IMEI format (should be 15 digits)"
    }

    if (data.deviceModel.isBlank()) {
        errors["deviceModel"] = "Device model is required"
    }
    if (data.costumerEmail.isBlank()) {
        errors["email"] = "Email is required"
    }

    if (data.costumerLoanNumer.isBlank()) {
        errors["loanNumber"] = "loan number is required"
    }

    return errors
}
