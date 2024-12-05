package com.trex.laxmiemi.ui.signupscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trex.laxmiemi.R
import com.trex.laxmiemi.ui.components.RexActionButton
import com.trex.laxmiemi.ui.createdevicescreen.FormField

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel(),
    onFinish: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(vertical = 50.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (uiState.showSuccessLogo) {
            Image(
                painter = painterResource(id = R.drawable.shield),
                contentDescription = "Success Logo",
                modifier =
                    Modifier
                        .size(120.dp)
                        .padding(bottom = 16.dp),
            )

            uiState.error?.let { error ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 20.dp),
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Filled.Error,
                        contentDescription = "",
                        tint = colorResource(R.color.red_300),
                    )
                    Spacer(Modifier.padding(end = 5.dp))
                    Text(
                        text = error,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.red_300),
                    )
                }
            }

            Text(
                text = "Sign-Up Successful!",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            RexActionButton("Sign in") { onFinish() }
        } else {
            Image(
                painter = painterResource(id = R.drawable.shield),
                contentDescription = "App Logo",
                modifier =
                    Modifier
                        .size(120.dp),
            )

            Text(
                text = "EMI Shield",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 32.sp,
                modifier = Modifier.padding(bottom = 20.dp, top = 20.dp),
            )

            FormField(
                "Shop name",
                uiState.shopName,
                null,
                viewModel::onShopNameChange,
                KeyboardOptions(keyboardType = KeyboardType.Text),
                Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            FormField(
                "Owner name",
                uiState.ownerName,
                null,
                viewModel::onOwnerNameChange,
                KeyboardOptions(keyboardType = KeyboardType.Text),
                Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            FormField(
                "Phone number",
                uiState.ownerPhone,
                null,
                viewModel::onPhoneChange,
                KeyboardOptions(keyboardType = KeyboardType.Phone),
                Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(8.dp))
            FormField(
                "Email",
                uiState.email,
                null,
                viewModel::onEmailChange,
                KeyboardOptions(keyboardType = KeyboardType.Email),
                Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            FormField(
                "Password",
                uiState.password,
                null,
                viewModel::onPasswordChange,
                KeyboardOptions(keyboardType = KeyboardType.Text),
                Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            FormField(
                "Re enter password",
                uiState.reEnterPassword,
                null,
                viewModel::onReEnterPasswordChange,
                KeyboardOptions(keyboardType = KeyboardType.Text),
                Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = viewModel::onSignUpClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
                colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(R.color.primary),
                    ),
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                    )
                } else {
                    Text("Sign Up", color = Color.White)
                }
            }
        }
    }
}
