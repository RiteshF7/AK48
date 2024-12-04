package com.trex.laxmiemi.ui.loginscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trex.laxmiemi.R
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
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Logo
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.shield),
            contentDescription = "App Logo",
            modifier =
                Modifier
                    .size(120.dp)
                    .padding(bottom = 32.dp),
        )

        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = viewModel::onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading,
            colors =
                ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF4CAF50), // Material Green
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
            Text("Sign Up")
        }

        uiState.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}
