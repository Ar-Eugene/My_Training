package com.example.core.di

import com.example.core.domain.interactor.ExamInteractor
import com.example.core.domain.interactor.ExamInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun examInteractorBinds(examInteractorImpl: ExamInteractorImpl): ExamInteractor
}