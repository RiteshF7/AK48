package com.trex.laxmiemi.ui.createdevicescreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun SearchBox(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    var textState by remember { mutableStateOf(TextFieldValue(query)) }

    OutlinedTextField(
        value = textState,
        onValueChange = { newValue ->
            textState = newValue
            onQueryChanged(newValue.text)
        },
        colors =
        OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Gray,
        ),
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
        label = { Text("Search", color = Color.White) },
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = { onSearch(textState.text) }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                )
            }
        },
    )
}
