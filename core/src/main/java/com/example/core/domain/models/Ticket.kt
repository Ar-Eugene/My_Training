package com.example.core.domain.models

/**
 * Билет (содержит несколько заданий)
 */

data class Ticket(
    val id: String,
    val number: Int,
    val tasks: List<Task>,
)
