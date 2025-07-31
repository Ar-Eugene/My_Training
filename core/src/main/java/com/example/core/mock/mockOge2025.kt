package com.example.core.mock

import com.example.core.domain.models.AnswerType
import com.example.core.domain.models.Essay
import com.example.core.domain.models.Question
import com.example.core.domain.models.Task
import com.example.core.domain.models.Ticket

/**
 * Пример mock данных для ОГЭ Русский 2025
 */


val mockOgeTicketsRus2025 = listOf(
    Ticket(
        id = "oge_rus_2025_t1",
        number = 1,
        tasks = listOf(
            Task(
                id = "oge_rus_2025_t1_task1",
                number = 1,
                questions = listOf(
                    Question(
                        id = "oge_rus_2025_t1_task1_q1",
                        text = "Большая ли Россия?",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("да", "нет", "не очень", "маленькая"),
                        correctAnswers = listOf("да")
                    )
                )
            )
        )
    )
)

val mockOgeEssaysRus2025 = listOf(
    Essay(
        id = "oge_rus_2025_essay1",
        title = "Опишите признаки ОРВИ",
        description = "Боль в горле и так далее"
    )
)