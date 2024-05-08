package com.nexusfalcao.login.state

sealed interface LoginUiState {
    object Started : LoginUiState
    object Loading : LoginUiState
    object Success : LoginUiState
    object Failure : LoginUiState
    object Error : LoginUiState
}