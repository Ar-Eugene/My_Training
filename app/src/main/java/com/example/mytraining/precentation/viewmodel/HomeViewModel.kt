package com.example.mytraining.precentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.core.domain.interactor.ExamInteractor
import com.example.core.domain.models.ExamType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val examInteractor: ExamInteractor,
) : ViewModel() {

    fun getExamScreen(): ExamType {
        return examInteractor.getExamScreen()
    }

    fun navigateToYearSelection(subjectId: String, onNavigate: (String) -> Unit) {
        onNavigate("year-selection/$subjectId")
    }
}
