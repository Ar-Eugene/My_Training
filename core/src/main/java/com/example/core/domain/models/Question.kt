package com.example.core.domain.models

data class Question(
    val id: String,
    val text: String,
    val imageUrl: String? = null,
    val answerType: AnswerType,
    val options: List<String>? = null, // Варианты ответов (если есть)
    val correctAnswers: List<String>, // Может быть несколько для MULTIPLE_CHOICE
)
