package com.trex.laxmiemi.ui.signupscreen

import androidx.lifecycle.ViewModel
import com.trex.rexnetwork.domain.firebasecore.firesstore.Shop
import com.trex.rexnetwork.domain.firebasecore.firesstore.ShopFirestore
import com.trex.rexnetwork.domain.firebasecore.firesstore.User
import com.trex.rexnetwork.domain.firebasecore.firesstore.UserFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()
    private val userFirestore = UserFirestore()
    private val shopFirestore = ShopFirestore()

    fun onShopNameChange(name: String) {
        _uiState.update { it.copy(shopName = name) }
    }

    fun onOwnerNameChange(name: String) {
        _uiState.update { it.copy(ownerName = name) }
    }

    fun onPhoneChange(phone: String) {
        _uiState.update { it.copy(ownerPhone = phone) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onReEnterPasswordChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onSignUpClick() {
        if (!validateInputs()) return
        setLoadingState(true)
        createUser()
    }

    private fun validateInputs(): Boolean {
        val state = uiState.value
        if (state.shopName.isBlank() ||
            state.ownerName.isBlank() ||
            state.ownerPhone.isBlank() ||
            state.email.isBlank() ||
            state.password.isBlank()
        ) {
            _uiState.update { it.copy(error = "All fields are required") }
            return false
        } else {
            if (state.password != state.reEnterPassword) {
                _uiState.update { it.copy(error = "Both password should be same!") }
                return false
            }
        }
        return true
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading, error = null) }
    }

    private fun createUser() {
        val user = User(uiState.value.email, uiState.value.password)

        userFirestore.createUser(
            user,
            onUserAlreadyExists = { handleUserAlreadyExists() },
            onUserCreatedSuccessfully = { handleUserCreated(user) },
            onUserCreationFailed = { handleError("Failed to create user. Please try again.") },
        )
    }

    private fun handleUserAlreadyExists() {
        _uiState.update {
            it.copy(
                isLoading = false,
                error = "User already exists. Please sign in with this email.",
                shouldFinish = true,
            )
        }
    }

    private fun handleUserCreated(user: User) {
        val shop =
            Shop(
                shopName = uiState.value.shopName,
                ownerName = uiState.value.ownerName,
                shopPhoneNumber = uiState.value.ownerPhone,
            )

        createShop(user.shopId, shop)
    }

    private fun createShop(
        shopId: String,
        shop: Shop,
    ) {
        shopFirestore.createOrUpdateShop(
            shopId,
            shop,
            onSuccess = { handleShopCreated(shopId) },
            onFailure = { handleError("Failed to create shop. Please try again.") },
        )
    }

    private fun handleShopCreated(shopId: String) {
        _uiState.update {
            it.copy(
                isLoading = false,
                signUpSuccess = true,
                shopId = shopId,
                showSuccessLogo = true,
            )
        }
    }

    private fun handleError(message: String): () -> Unit =
        {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = message,
                )
            }
        }
}

data class SignUpUiState(
    val shopName: String = "",
    val ownerName: String = "",
    val ownerPhone: String = "",
    val email: String = "",
    val password: String = "",
    val reEnterPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val signUpSuccess: Boolean = false,
    val shopId: String = "",
    val showSuccessLogo: Boolean = false,
    val shouldFinish: Boolean = false,
)
