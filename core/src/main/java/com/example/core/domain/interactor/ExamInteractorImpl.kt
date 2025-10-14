package com.example.core.domain.interactor

import com.example.core.domain.models.ExamType
import com.example.core.domain.repository.ExamPreferencesRepository
import javax.inject.Inject

class ExamInteractorImpl @Inject constructor(private val examPreferencesRepository: ExamPreferencesRepository) :
    ExamInteractor {
    override suspend fun saveExamScreen(examType: ExamType) {
        return examPreferencesRepository.saveExamType(examType)
    }

    override fun getExamScreen(): ExamType {
        return examPreferencesRepository.getExamType()
    }

    override fun isExamTypeSelected(): Boolean {
        return examPreferencesRepository.isExamTypeSelected()
    }
}