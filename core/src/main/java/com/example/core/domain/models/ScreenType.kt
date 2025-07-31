package com.example.core.domain.models

/**
 * Инстанции, для перехода в нужный раздел
 */

enum class ScreenType {
    TICKETS,     // Общий для всех (билеты)
    TASKS,       // Общий для всех (отдельные задания)
    ORAL_PART,   // Только для ЕГЭ (устная часть)
    ESSAYS       // Только для ОГЭ (сочинения)
}