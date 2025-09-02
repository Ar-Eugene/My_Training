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
                    ),
                    Question(
                        id = "oge_eng_2024_t1_task1_q2",
                        text = "Object ?",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("o", "b", "j", "k"),
                        correctAnswers = listOf("o")
                    )
                )
            ),
            Task(
                id = "oge_eng_2024_t1_task2",
                number = 2,
                questions = listOf(
                    Question(
                        id = "oge_eng_2024_t1_task2_q1",
                        text = "Where W ?",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("w", "t", "y", "a"),
                        correctAnswers = listOf("w")
                    )
                )
            )
        )
    ),
    Ticket(
        id = "oge_eng_2024_t2",
        number = 2,
        tasks = listOf(
            Task(
                id = "oge_eng_2024_t2_task1",
                number = 1,
                questions = listOf(
                    Question(
                        id = "oge_eng_2024_t2_task1_q1",
                        text = "Where W ?",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("w", "t", "y", "a"),
                        correctAnswers = listOf("w")
                    ),
                    Question(
                        id = "oge_eng_2024_t2_task1_q2",
                        text = "Where G ?",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("g", "w", "y", "a"),
                        correctAnswers = listOf("g")
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