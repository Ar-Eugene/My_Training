package com.example.core.mock

import com.example.core.domain.models.AnswerType
import com.example.core.domain.models.Essay
import com.example.core.domain.models.Question
import com.example.core.domain.models.Task
import com.example.core.domain.models.Ticket

/**
 * Пример mock данных для ОГЭ Английский 2024
 */

val mockOgeTicketsEng2024 = listOf(
    Ticket(
        id = "oge_eng_2024_t1",
        number = 1,
        tasks = listOf(
            Task(
                id = "oge_eng_2024_t1_task1",
                number = 1,
                questions = listOf(
                    Question(
                        id = "oge_eng_2024_t1_task1_q1",
                        text = "Object on Eath?",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("1", "2", "3", "4"),
                        correctAnswers = listOf("2")
                    )
                )
            )
        )
    )
)

// Пример mock данных для сочинений ОГЭ
val mockOgeEssaysEng2024 = listOf(
    Essay(
        id = "oge_eng_2024_essay1",
        title = "Write object",
        description = "object"
    )
)