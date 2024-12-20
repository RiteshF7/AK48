package com.trex.laxmiemi.ui.createdevicescreen

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FormField(
    label: String,
    value: String,
    error: String?,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
) {
    val context = LocalContext.current

    var textState by remember { mutableStateOf(value) }

    OutlinedTextField(
        value = textState,
        onValueChange = {
            textState = it
            onValueChange(it)
        },
        label = { Text(label) },
        isError = error != null,
        keyboardOptions = keyboardOptions,
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    showDatePickerDialog(context) { selectedDate ->
                        val formattedDate = formatDate(selectedDate)
                        textState = formattedDate
                        onValueChange(formattedDate)
                    }
                },
        readOnly = true,
    )

    if (error != null) {
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

fun showDatePickerDialog(
    context: Context,
    onDateSelected: (Calendar) -> Unit,
) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(year, month, dayOfMonth)
            onDateSelected(calendar)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
    ).show()
}

fun formatDate(calendar: Calendar): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(calendar.time)
}
