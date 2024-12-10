package com.nexusfalcao.login

import android.content.Intent
import com.nexusfalcao.authentication.GoogleAuthenticator
import com.nexusfalcao.authentication.exception.CancelledAuthException
import com.nexusfalcao.data.repository.UserRepository
import com.nexusfalcao.login.state.LoginUiState
import com.nexusfalcao.model.User
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginVMTest {
    private lateinit var viewModel: LoginViewModel
    private val userRepository: UserRepository = mockk(relaxed = true)
    private val googleAuthenticator: GoogleAuthenticator = mockk(relaxed = true)

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = LoginViewModel(userRepository, googleAuthenticator)
    }

    @Test
    fun processSignInGoogle_updatesUiStateToSuccess_whenUserIsSaved() = runTest {
        val intent = mockk<Intent>()
        val user = mockk<User>()
        coEvery { googleAuthenticator.processGoogleSignInResult(any()) } returns user
        coEvery { userRepository.insertUser(user) } returns true

        viewModel.processSignInGoogle(intent)

        assertEquals(LoginUiState.Success, viewModel.loginUiState.first())
    }

    @Test
    fun processSignInGoogle_updatesUiStateToError_whenUserIsNotSaved() = runTest {
        val intent = mockk<Intent>()
        val user = mockk<User>()
        coEvery { googleAuthenticator.processGoogleSignInResult(any()) } returns user
        coEvery { userRepository.insertUser(user) } returns false

        viewModel.processSignInGoogle(intent)

        assertEquals(LoginUiState.Error, viewModel.loginUiState.first())
    }

    @Test
    fun processSignInGoogle_updatesUiStateToFailure_whenAuthIsCancelled() = runTest {
        val intent = mockk<Intent>()
        coEvery {
            googleAuthenticator.processGoogleSignInResult(intent)
        } throws CancelledAuthException("Canceled by user")

        viewModel.processSignInGoogle(intent)

        assertEquals(LoginUiState.Failure, viewModel.loginUiState.first())
    }

    @Test
    fun processSignInGoogle_updatesUiStateToError_whenExceptionIsThrown() = runTest {
        val intent = mockk<Intent>()
        coEvery { googleAuthenticator.processGoogleSignInResult(any()) } throws Exception()

        viewModel.processSignInGoogle(intent)

        assertEquals(LoginUiState.Error, viewModel.loginUiState.first())
    }

    @Test
    fun startSignInLoading_updatesUiStateToLoading() = runTest {
        viewModel.startSignInLoading()

        assertEquals(LoginUiState.Loading, viewModel.loginUiState.first())
    }

    @Test
    fun showSignInError_updatesUiStateToError() = runTest {
        viewModel.showSignInError()

        assertEquals(LoginUiState.Error, viewModel.loginUiState.first())
    }
}