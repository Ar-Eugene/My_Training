package com.example.core.domain.models

/**
 * Данные за конкретный год
 */
data class YearData(
    val year: Int,
    val tickets: List<Ticket>,
    val oralPart: OralPart? = null, // Для ЕГЭ
    val essays: List<Essay>? = null // Для ОГЭ
)
