package com.example.core.domain.interactor

import com.example.core.domain.ExamType

interface ExamInteractor {
    suspend fun saveExamType(examType: ExamType)
    suspend fun getExamType(): ExamType
}