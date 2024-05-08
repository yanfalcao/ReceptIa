package com.nexusfalcao.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexusfalcao.authentication.GoogleAuthenticator
import com.nexusfalcao.authentication.exception.CancelledAuthException
import com.nexusfalcao.data.repository.UserRepository
import com.nexusfalcao.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    val googleAuthenticator: GoogleAuthenticator
) : ViewModel() {
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Started)
    val loginUiState get() = _loginUiState

    fun processSignInGoogle(intent: Intent) {
        viewModelScope.launch {
            try {
                _loginUiState.value = LoginUiState.Loading
                val user = googleAuthenticator.processGoogleSignInResult(intent)
                val isSaved = userRepository.insertUser(user)

                _loginUiState.value = when(isSaved) {
                    true -> LoginUiState.Success
                    false -> LoginUiState.Error
                }
            } catch (e: Exception) {
                _loginUiState.value = when(e) {
                    is CancelledAuthException -> LoginUiState.Failure
                    else -> LoginUiState.Error
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
