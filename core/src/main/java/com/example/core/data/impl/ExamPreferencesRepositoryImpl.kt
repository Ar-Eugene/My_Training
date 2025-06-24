package com.example.core.data.impl

import com.example.core.domain.ExamType
import com.example.core.domain.repository.ExamPreferencesRepository

class ExamPreferencesRepositoryImpl():ExamPreferencesRepository {
    override suspend fun saveExamType(examType: ExamType) {
        TODO("Not yet implemented")
    }

    override suspend fun getExamType(): ExamType {
        TODO("Not yet implemented")
    }
}