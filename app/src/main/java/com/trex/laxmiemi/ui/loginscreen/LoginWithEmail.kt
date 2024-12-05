package com.trex.laxmiemi.ui.loginscreen

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.trex.laxmiemi.ui.createdevicescreen.FormField
import com.trex.rexnetwork.domain.firebasecore.firesstore.User

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onSignUpClick: () -> Unit,
    onLoginSuccess: (User) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.loginSuccess) {
        onLoginSuccess(uiState.user)
    }
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Logo
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.shield),
            contentDescription = "App Logo",
            modifier =
                Modifier
                    .size(150.dp)
                    .padding(bottom = 32.dp),
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
            KeyboardOptions(keyboardType = KeyboardType.Password),
            Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = viewModel::onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading,
            colors =
                ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.primary), // Material Green
                ),
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                )
            } else {
                Text("Login", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onSignUpClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Sign Up",
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
