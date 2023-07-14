package com.example.receptia.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Started)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    // TODO: Implement login logic
    fun loginGoogle() {
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Success(data = true)
        }
    }
}
