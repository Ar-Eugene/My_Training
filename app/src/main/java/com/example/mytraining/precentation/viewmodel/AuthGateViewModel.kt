package com.example.mytraining.precentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.core.domain.interactor.ExamInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthGateViewModel @Inject constructor(
    private val examInteractor: ExamInteractor
) : ViewModel() {

    fun isExamTypeSelected(): Boolean = examInteractor.isExamTypeSelected()
}



