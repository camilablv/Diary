package com.pchpsky.diary.presentation.auth

import androidx.lifecycle.ViewModel
import com.pchpsky.diary.data.network.exceptions.NetworkError
import com.pchpsky.diary.presentation.auth.interfaces.AuthController
import com.pchpsky.diary.presentation.auth.interfaces.LoginViewModel
import com.pchpsky.diary.presentation.auth.interfaces.SignupViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

enum class FieldKey(val key: String) {
    EMAIL("email"),
    PASSWORD("password"),
    CONFIRM_PASSWORD("confirm password")
}

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthController) : ViewModel(), LoginViewModel, SignupViewModel {

    private val _uiState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.None)

    override val uiState: StateFlow<AuthState> = _uiState

    override suspend fun login(login: String, password: String) {
        _uiState.value = AuthState.Loading

        repository.login(login, password).fold(
            {
                when(it) {
                    is NetworkError.ServerError -> { AuthState.ServerError }
                    is NetworkError.AuthenticationError -> { AuthState.AuthenticationError(it.message) }
                    is NetworkError.ValidationError -> { AuthState.ValidationError(it.fields) }
                }
            },
            {
                AuthState.SignupSuccessful
            }
        ).also { _uiState.value = it }
    }

    override suspend fun createUser(email: String, password: String, passwordConfirmation: String) {
        if (!password.contentEquals(passwordConfirmation)) {
            _uiState.value = AuthState.ValidationError(mapOf(FieldKey.CONFIRM_PASSWORD.key to arrayListOf("Does not mach password")))
            return
        }
        _uiState.value = AuthState.Loading

        repository.createUserAndSaveUserToken(email, password).fold(
            {
                when(it) {
                    NetworkError.ServerError -> { AuthState.ServerError }
                    is NetworkError.AuthenticationError -> { AuthState.AuthenticationError(it.message) }
                    is NetworkError.ValidationError -> { AuthState.ValidationError(it.fields) }
                }
            },
            {
                AuthState.SignupSuccessful
            }
        ).also { _uiState.value = it }
    }
}

object FakeAuthViewModel : LoginViewModel, SignupViewModel {
    override val uiState: StateFlow<AuthState> = MutableStateFlow(AuthState.ValidationError(emptyMap()))
    override suspend fun createUser(email: String, password: String, passwordConfirmation: String) {}
    override suspend fun login(login: String, password: String) {}
}