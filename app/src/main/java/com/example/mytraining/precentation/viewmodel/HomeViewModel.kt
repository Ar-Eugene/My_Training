package com.example.mytraining.precentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.ExamType
import com.example.core.domain.interactor.ExamInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val examInteractor: ExamInteractor) : ViewModel() {

    fun savaExamScreen(examType:ExamType){
        viewModelScope.launch {
            examInteractor.saveExamType(examType)
        }
    }

    suspend fun getExamScreen():ExamType{
       return examInteractor.getExamType()
    }
}