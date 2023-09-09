package com.example.receptia.feature.login.state

sealed interface LoginUiState {
    object Started : LoginUiState
    object Loading : LoginUiState

    data class Success(
        val data: Boolean,
    ) : LoginUiState
}