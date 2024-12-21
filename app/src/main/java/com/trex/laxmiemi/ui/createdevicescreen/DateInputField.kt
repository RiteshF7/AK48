package com.trex.laxmiemi.ui.createdevicescreen

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        maxLines = 1,
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
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Green,
                focusedTextColor = Color.Green,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.Green,
                unfocusedLabelColor = Color.White,
                unfocusedBorderColor = Color.Gray,
            ),
        readOnly = false,
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
