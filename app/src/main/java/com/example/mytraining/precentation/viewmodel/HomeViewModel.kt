package com.example.mytraining.precentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.interactor.ExamInteractor
import com.example.core.domain.models.ExamType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val examInteractor: ExamInteractor
) : ViewModel() {

    fun getExamScreen(): ExamType {
        return examInteractor.getExamScreen()
    }

    fun savaExamScreen(examType: ExamType) {
        viewModelScope.launch {
            examInteractor.saveExamScreen(examType)
        }
    }
    
    fun navigateToYearSelection(subjectId: String, onNavigate: (String) -> Unit) {
        onNavigate("year-selection/$subjectId")
    }
}
