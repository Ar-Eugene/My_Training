package com.example.core.domain.models

/**
 * Предмет
 */

data class Subject(
    val id: String,
    val name: String,
    val examType: ExamType,
    val years: List<YearData>,
)
