package com.example.core.domain.models

data class AnswerState(
    val answer: Any? = null,         // String, Set<String>
    val confirmed: Boolean = false   // подтверждён ли выбор
)
