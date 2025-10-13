package com.example.feature_register.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName
    private val _login = MutableStateFlow("")
    val login: StateFlow<String> = _login

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    // Ошибки
    private val _userNameError = MutableStateFlow<String?>(null)
    val userNameError: StateFlow<String?> = _userNameError

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    // Кнопка доступна только если всё корректно
    val isRegisterEnabled: StateFlow<Boolean> = combine(
        combine(_userName, _login, _password) { name, login, pass ->
            name.isNotBlank() && login.isNotBlank() && pass.isNotBlank()
        },
        combine(_userNameError, _loginError, _passwordError) { nameErr, loginErr, passErr ->
            nameErr == null && loginErr == null && passErr == null
        }
    ) { fieldsOk, errorsOk ->
        fieldsOk && errorsOk
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun stateUserName(userName: String) {
        _userName.value = userName
        validateUserName(userName)
    }

    fun stateLogin(login: String) {
        _login.value = login
        validateLogin(login)
    }

    fun statePassword(password: String) {
        _password.value = password
        validatePassword(password)
    }

    // ---- Валидация ----
    private fun validateUserName(name: String) {
        _userNameError.value = when {
            name.isBlank() -> "Введите имя"
            name.length < 2 -> "Имя должно содержать не менее 2-х символов"
            else -> null
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
        val hasUppercase = password.any { it.isUpperCase() }
        _passwordError.value = when {
            password.isBlank() -> "Введите пароль"
            password.length < 4 -> "Пароль должен содержать не менее 4-х символов"
            !hasUppercase -> "Пароль должен содержать хотя бы одну заглавную букву"
            else -> null
        }
    }
}