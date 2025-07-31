package com.example.core.domain.repository

import com.example.core.domain.models.ExamType

interface ExamPreferencesRepository {
    suspend fun saveExamType(examType: ExamType)
    suspend fun getExamType(): ExamType
}