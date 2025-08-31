package com.example.core.domain.interactor

import com.example.core.domain.models.ExamType

interface ExamInteractor {
    suspend fun saveExamScreen(examType: ExamType)
    fun getExamScreen(): ExamType
}