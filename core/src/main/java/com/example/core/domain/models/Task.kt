package com.example.core.domain.models

/**
 * Задание
 */

data class Task(
    val id: String,
    val number: Int, // Номер задания (1-10 для ОГЭ, 1-27 для ЕГЭ)
    val questions: List<Question>,
)
