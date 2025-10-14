package com.example.feature_register.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.interactor.ExamInteractor
import com.example.core.domain.models.ExamType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SelectExamViewModel @Inject constructor(
    private val examInteractor: ExamInteractor
) : ViewModel() {

    fun saveExamType(examType: ExamType) {
        viewModelScope.launch {
            examInteractor.saveExamScreen(examType)
        }
    }
}


