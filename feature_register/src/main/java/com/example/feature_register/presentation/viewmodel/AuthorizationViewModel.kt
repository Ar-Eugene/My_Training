package com.example.feature_register.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.interactor.AuthInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AuthorizationUiEvent {
    data object LoginSuccess : AuthorizationUiEvent
}

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
) : ViewModel() {

    private val _login = MutableStateFlow("")
    val login: StateFlow<String> = _login

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _events = Channel<AuthorizationUiEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    val isLoginEnabled: StateFlow<Boolean> = combine(
        combine(_login, _password) { login, pass ->
            login.isNotBlank() && pass.isNotBlank()
        },
        combine(_loginError, _passwordError) { loginErr, passErr ->
            loginErr == null && passErr == null
        },
        _isLoading,
    ) { fieldsOk, errorsOk, isLoading ->
        fieldsOk && errorsOk && !isLoading
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun stateLogin(login: String) {
        _login.value = login
        validateLogin(login)
    }

    fun statePassword(password: String) {
        _password.value = password
        validatePassword(password)
    }

    fun login() {
        if (!isLoginEnabled.value) return

        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null

            authInteractor.signIn(
                login = _login.value.trim(),
                password = _password.value,
            ).onSuccess {
                _events.send(AuthorizationUiEvent.LoginSuccess)
            }.onFailure { error ->
                _authError.value = error.message
            }

            _isLoading.value = false
        }
    }

    private fun validateLogin(login: String) {
        _loginError.value = when {
            login.isBlank() -> "Введите логин"
            login.length < 4 -> "Логин должен содержать не менее 4-х символов"
            else -> null
        }
    }

    private fun validatePassword(password: String) {
        _passwordError.value = when {
            password.isBlank() -> "Введите пароль"
            password.length < 6 -> "Пароль должен содержать не менее 6-ти символов"
            else -> null
        }
    }
}
