package com.example.receptia.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.login.state.LoginUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Started)
    val loginUiState get() = _loginUiState

    // TODO: Implement login logic
    fun loginGoogle() {
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Loading
            delay(2000)
            _loginUiState.value = LoginUiState.Success(data = true)
        }
    }
}
