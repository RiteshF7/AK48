package com.trex.laxmiemi.ui.loginscreen

import androidx.lifecycle.ViewModel
import com.trex.rexnetwork.domain.firebasecore.firesstore.User
import com.trex.rexnetwork.domain.firebasecore.firesstore.UserFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    private val userFirestore = UserFirestore()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    private fun validateInputs(): Boolean {
        val state = uiState.value
        return (state.email.isNotBlank() && state.password.isNotBlank())
    }

    fun onLoginClick() {
        if (!validateInputs()) {
            _uiState.update { it.copy(error = "Email and passowrd are required!") }
            return
        }
        _uiState.update { it.copy(isLoading = true) }
        userFirestore.getUserIfExists(_uiState.value.email, { user ->
            if (user.password == _uiState.value.password) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginSuccess = true,
                        user = user,
                        error = null,
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Incorrect password",
                    )
                }
            }
        }, {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = "No user found with this email",
                )
            }
        })
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val user: User = User(),
    val error: String? = null,
    val loginSuccess: Boolean = false,
)
