package com.example.core.domain.models

/**
 * Устная часть (для ЕГЭ)
 */

data class OralPart(
    val id: String,
    val tickets: List<Ticket>,// 20 билетов по 4 задания
)
