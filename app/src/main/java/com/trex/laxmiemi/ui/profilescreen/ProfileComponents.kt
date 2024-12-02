package com.trex.laxmiemi.ui.profilescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.trex.laxmiemi.R
import com.trex.laxmiemi.ui.components.RexActionButton

@Composable
fun ProfileScreen(
    vm: ProfileViewModel,
    onFinish: () -> Unit,
) {
    val uiState by vm.profileState
    var isEditing by remember { mutableStateOf(false) }
    var editedShopName by remember { mutableStateOf(uiState.shopName) }
    var editedOwnerName by remember { mutableStateOf(uiState.ownerName ?: "") }

    Column(
        modifier = Modifier.background(color = Color.Black.copy(alpha = 0.85f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        ProfileHeader(
            uiState = uiState,
            isEditing = isEditing,
            editedShopName = editedShopName,
            onShopNameChange = { editedShopName = it },
            onEditClick = { isEditing = true },
            onSaveClick = {
                vm.updateProfile(editedOwnerName, editedShopName)
                isEditing = false
            },
        )
        HorizontalDivider(thickness = 2.dp)
        ProfileDetails(
            shop = uiState,
            isEditing = isEditing,
            editedOwnerName = editedOwnerName,
            onOwnerNameChange = { editedOwnerName = it },
            onFinish = onFinish,
        )
    }
}

@Composable
private fun ProfileHeader(
    uiState: ProfileState,
    isEditing: Boolean,
    editedShopName: String,
    onShopNameChange: (String) -> Unit,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(
                onClick = if (isEditing) onSaveClick else onEditClick,
            ) {
                Icon(
                    imageVector = if (isEditing) Icons.Default.Save else Icons.Default.Edit,
                    contentDescription = if (isEditing) "Save" else "Edit",
                    tint = Color.White,
                )
            }
        }

        Card(
            colors =
                CardDefaults.cardColors(
                    containerColor = Color.Green.copy(alpha = 0.1f),
                    contentColor = Color.White,
                ),
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "",
                tint = Color(0xFF00C853),
                modifier =
                    Modifier
                        .size(92.dp)
                        .padding(5.dp),
            )
        }
        Spacer(modifier = Modifier.height(18.dp))

        if (isEditing) {
            OutlinedTextField(
                value = editedShopName,
                onValueChange = onShopNameChange,
                label = { Text("Shop Name", color = Color.White) },
                textStyle =
                    TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                    ),
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                    ),
            )
        } else {
            Text(
                textAlign = TextAlign.Center,
                text = uiState.shopName,
                style =
                    TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                    ),
            )
        }
    }
}

@Composable
private fun ProfileDetails(
    shop: ProfileState,
    isEditing: Boolean,
    editedOwnerName: String,
    onOwnerNameChange: (String) -> Unit,
    onFinish: () -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextInfo("Shop ID:", shop.shopId, editable = false)

        TextInfo(
            key = "Owner name:",
            value = if (isEditing) editedOwnerName else (shop.ownerName ?: ""),
            editable = isEditing,
            onValueChange = onOwnerNameChange,
        )
        TextInfo("Owner number :", shop.ownerNumber, editable = false)
        TextInfo("Token Balance:", shop.tokenBalance, editable = false)
        TextInfo("Active devices:", shop.activeDevice, editable = false)
        TextInfo("DeActivated devices:", shop.deactivatedDevices, editable = false)

        Spacer(Modifier.weight(1f))
        HorizontalDivider(Modifier.height(2.dp))
        if (!isEditing) {
            RexActionButton("Logout") {
                Firebase.auth.signOut()
                onFinish()
            }
        }
    }
}

@Composable
private fun TextInfo(
    key: String,
    value: String,
    editable: Boolean = false,
    onValueChange: (String) -> Unit = {},
) {
    Column {
        Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = key,
                style =
                    TextStyle(
                        fontFamily = FontFamily(Font(R.font.opensans_regular)),
                        fontSize = 16.sp,
                        color = Color.White,
                    ),
            )
            Spacer(Modifier.weight(1f))

            if (editable) {
                OutlinedTextField(
                    modifier = Modifier.padding(start = 10.dp),
                    value = value,
                    onValueChange = onValueChange,
                    textStyle =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.opensans_bold)),
                            fontSize = 16.sp,
                            color = colorResource(R.color.primary),
                        ),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.primary),
                            unfocusedBorderColor = Color.Gray,
                        ),
                )
            } else {
                Text(
                    text = value,
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.opensans_bold)),
                            fontSize = 16.sp,
                            color = colorResource(R.color.primary),
                        ),
                )
            }
        }
        HorizontalDivider(thickness = 2.dp)
    }
}
