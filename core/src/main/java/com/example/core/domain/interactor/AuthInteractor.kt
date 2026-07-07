package com.example.core.domain.interactor

import com.example.core.domain.models.User

interface AuthInteractor {
    suspend fun register(userName: String, login: String, password: String): Result<Unit>
    suspend fun signIn(login: String, password: String): Result<Unit>
    fun signOut()
    fun isLoggedIn(): Boolean
    suspend fun getCurrentUser(): Result<User>
}
