package com.example.feature_oge.di

import com.example.feature_oge.presentation.viewmodel.OgeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object OgeModule {
    
    @Provides
    @ViewModelScoped
    fun provideOgeViewModel(): OgeViewModel {
        return OgeViewModel()
    }
}

