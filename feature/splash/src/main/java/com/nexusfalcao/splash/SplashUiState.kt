package com.nexusfalcao.splash

sealed interface SplashUiState {
    object Loading : SplashUiState

    data class Success(
        val logged: Boolean,
    ) : SplashUiState
}
