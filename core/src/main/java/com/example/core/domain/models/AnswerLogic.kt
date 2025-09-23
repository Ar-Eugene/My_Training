package com.example.core.domain.models

import androidx.compose.ui.graphics.Color

data class AnswerLogic(
    val isAnswerValid: Boolean,
    val isAnswered: Boolean,
    val isCorrect: Boolean,
    val backgroundColor: Color
)