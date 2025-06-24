package com.example.core.domain.interactor

import com.example.core.domain.ExamType
import com.example.core.domain.repository.ExamPreferencesRepository

class ExamInteractorImpl(private val preferencesRepository: ExamPreferencesRepository) {
     suspend fun saveExamType(examType: ExamType) {
        return preferencesRepository.saveExamType(examType)
    }

     suspend fun getExamType(): ExamType {
        return preferencesRepository.getExamType()
    }
}