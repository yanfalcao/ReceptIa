package com.nexusfalcao.receptia.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexusfalcao.receptia.feature.login.state.LoginUiState
import com.nexusfalcao.receptia.model.SignInErrorStatus
import com.nexusfalcao.receptia.model.SignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Started)
    val loginUiState get() = _loginUiState

    fun processSignInGoogle(signInResult: SignInResult) {
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Loading
            if(signInResult.data != null) {
                signInResult.data.create()
                _loginUiState.value = LoginUiState.Success
            } else {
                _loginUiState.value = when(signInResult.error?.status) {
                    null -> LoginUiState.Error
                    SignInErrorStatus.CANCELLED -> LoginUiState.Failure
                    SignInErrorStatus.GENERIC -> LoginUiState.Error
                }
            }
        }
    }

    fun startSignInLoading() {
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Loading
        }
    }

    fun showSignInError() {
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Error
        }
    }
}
