package com.example.core.data.impl

import com.example.core.domain.models.User
import com.example.core.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : AuthRepository {

    override suspend fun register(
        userName: String,
        login: String,
        password: String,
    ): Result<Unit> = runCatching {

        val email = loginToEmail(login)

        val authResult = firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .await()

        val user = authResult.user
            ?: throw IllegalStateException("Не удалось создать пользователя")

        createUserDocument(
            uid = user.uid,
            userName = userName,
            login = login,
            email = email
        )

        Unit

    }.mapAuthError()

    override suspend fun signIn(
        login: String,
        password: String,
    ): Result<Unit> = runCatching {

        firebaseAuth
            .signInWithEmailAndPassword(loginToEmail(login), password)
            .await()

        Unit

    }.mapAuthError()

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    /**
     * Создание документа пользователя в Firestore
     */
    private suspend fun createUserDocument(
        uid: String,
        userName: String,
        login: String,
        email: String,
    ) {

        val userData = hashMapOf(
            "userName" to userName.trim(),
            "login" to login.trim(),
            "email" to email,
            "createdAt" to FieldValue.serverTimestamp(),
        )

        firestore
            .collection(USERS_COLLECTION)
            .document(uid)
            .set(userData)
            .await()
    }

    /**
     * Firebase требует email.
     * Пользователь вводит только логин.
     */
    private fun loginToEmail(login: String): String {
        val normalized = login.trim().lowercase()
        return if (normalized.contains("@")) {
            normalized
        } else {
            "$normalized@mytraining.app"
        }
    }

    /**
     * Красивое отображение ошибок Firebase
     */
    private fun <T> Result<T>.mapAuthError(): Result<T> =
        fold(
            onSuccess = {
                Result.success(it)
            },
            onFailure = {
                Result.failure(mapException(it))
            }
        )

    private fun mapException(throwable: Throwable): Exception {

        val message = when (throwable) {

            is FirebaseAuthUserCollisionException ->
                "Пользователь с таким логином уже существует"

            is FirebaseAuthWeakPasswordException ->
                "Пароль должен содержать минимум 6 символов"

            is FirebaseAuthInvalidCredentialsException ->
                "Неверный логин или пароль"

            else ->
                throwable.message ?: "Произошла неизвестная ошибка"

        }

        return Exception(message, throwable)
    }

    override suspend fun getCurrentUser(): Result<User> = runCatching {

        val firebaseUser = firebaseAuth.currentUser
            ?: error("Пользователь не авторизован")

        val snapshot = firestore
            .collection(USERS_COLLECTION)
            .document(firebaseUser.uid)
            .get()
            .await()

        User(
            id = firebaseUser.uid,
            userName = snapshot.getString("userName").orEmpty(),
            login = snapshot.getString("login").orEmpty(),
            email = snapshot.getString("email").orEmpty()
        )
    }

    private companion object {
        const val USERS_COLLECTION = "users"
    }
}
