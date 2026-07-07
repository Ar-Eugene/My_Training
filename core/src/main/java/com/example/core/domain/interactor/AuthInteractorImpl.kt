package com.example.core.domain.interactor

import com.example.core.domain.models.User
import com.example.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthInteractorImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : AuthInteractor {

    override suspend fun register(
        userName: String,
        login: String,
        password: String,
    ): Result<Unit> = authRepository.register(userName, login, password)

    override suspend fun signIn(login: String, password: String): Result<Unit> =
        authRepository.signIn(login, password)

    override fun signOut() = authRepository.signOut()

    override fun isLoggedIn(): Boolean = authRepository.isLoggedIn()

    override suspend fun getCurrentUser(): Result<User> =
        authRepository.getCurrentUser()
}

