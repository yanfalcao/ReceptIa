package com.example.receptia.feature.login.state

sealed interface LoginUiState {
    object Started : LoginUiState
    object Loading : LoginUiState
    object Success : LoginUiState
    object Failure : LoginUiState
    data class Error(
        val message: String?,
    ) : LoginUiState
}