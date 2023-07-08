package com.example.receptia.feature.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var loginState by mutableStateOf<LoginUiState>(LoginUiState.Started)

    // TODO: Implement login logic
    fun loginGoogle() {
        viewModelScope.launch {
            try {
                loginState = LoginUiState.Loading
                delay(3000)
            } catch (e: Exception) {
                // Lidar com erros, se necessário
            } finally {
                loginState = LoginUiState.Success(data = true)
            }
        }
    }
}
