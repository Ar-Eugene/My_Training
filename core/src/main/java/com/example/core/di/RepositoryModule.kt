package com.example.core.di

import com.example.core.data.impl.AuthRepositoryImpl
import com.example.core.data.impl.ExamPreferencesRepositoryImpl
import com.example.core.data.impl.QuestionRepositoryImpl
import com.example.core.domain.repository.AuthRepository
import com.example.core.domain.repository.ExamPreferencesRepository
import com.example.core.domain.repository.QuestionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun examRepositoryBinds(examPreferencesRepositoryImpl: ExamPreferencesRepositoryImpl):
            ExamPreferencesRepository

    @Binds
    abstract fun questionRepositoryBinds(questionRepositoryImpl: QuestionRepositoryImpl):
            QuestionRepository

    @Binds
    abstract fun authRepositoryBinds(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}
