package com.example.feature_register.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor() : ViewModel() {

    private val _login = MutableStateFlow("")
    val login: StateFlow<String> = _login

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun stateLogin(login: String) {
        _login.value = login
    }

    fun statePassword(password: String) {
        _password.value = password
    }
}