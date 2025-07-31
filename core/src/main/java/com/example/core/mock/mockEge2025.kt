package com.example.core.mock

import com.example.core.domain.models.AnswerType
import com.example.core.domain.models.OralPart
import com.example.core.domain.models.Question
import com.example.core.domain.models.Task
import com.example.core.domain.models.Ticket

/**
 * Пример mock данных для ЕГЭ Русский язык 2025
 */

val mockEgeTicketsRus2025 = listOf(

    /**
     * Билет 1
     */

    Ticket(
        id = "ege_rus_2025_t1",
        number = 1,
        tasks = listOf(
            Task(
                id = "ege_rus_2025_t1_task1",
                number = 1,
                questions = listOf(
                    Question(
                        id = "ege_rus_2025_t1_task1_q1",
                        text = "Поставьте в правильную форму: нет (700) стульев",
                        answerType = AnswerType.TEXT_ANSWER,
                        correctAnswers = listOf("семисот")
                    )
                )
            ),
            Task(
                id = "ege_rus_2025_t1_task2",
                number = 2,
                questions = listOf(
                    Question(
                        id = "ege_rus_2025_t1_task2_q1",
                        text = "Выберете слово с чередующейся гласной",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("2024", "2025", "дверь", "камета"),
                        correctAnswers = listOf("2025")
                    )
                )
            )
        )
    ),

    /**
     * Билет 2
     */

    Ticket(
        id = "ege_rus_2025_t2",
        number = 2,
        tasks = listOf(
            Task(
                id = "ege_rus_2025_t2_task1",
                number = 1,
                questions = listOf(
                    Question(
                        id = "ege_rus_2025_t2_task1_q1",
                        text = "Поставьте в правильную форму: нет (800) стульев",
                        answerType = AnswerType.TEXT_ANSWER,
                        correctAnswers = listOf("восьмисот")
                    )
                )
            ),
            Task(
                id = "ege_rus_2025_t2_task2",
                number = 2,
                questions = listOf(
                    Question(
                        id = "ege_rus_2025_t2_task2_q1",
                        text = "Слово с мягким звуком",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("нос", "конь", "слон", "зуб"),
                        correctAnswers = listOf("конь")
                    )
                )
            )
        )
    )
)

// Пример mock данных для устной части ЕГЭ
val mockEgeOralPartRus2025 = OralPart(
    id = "ege_rus_2025_oral",
    tickets = listOf(
        Ticket(
            id = "ege_rus_2025_oral_t1",
            number = 1,
            tasks = listOf(
                Task(
                    id = "ege_rus_2025_oral_t1_task1",
                    number = 1,
                    questions = listOf(
                        Question(
                            id = "ege_rus_2025_oral_t1_task1_q1",
                            text = "Объясните теорему Пифагора",
                            answerType = AnswerType.TEXT_ANSWER,
                            correctAnswers = listOf("В прямоугольном треугольнике квадрат гипотенузы равен сумме квадратов катетов")
                        )
                    )
                )
            )
        ),
        Ticket(
            id = "ege_rus_2025_oral_t2",
            number = 2,
            tasks = listOf(
                Task(
                    id = "ege_rus_2025_oral_t2_task1",
                    number = 1,
                    questions = listOf(
                        Question(
                            id = "ege_rus_2025_oral_t2_task1_q1",
                            text = "Объясните что-то",
                            answerType = AnswerType.TEXT_ANSWER,
                            correctAnswers = listOf("Что-то")
                        ),
                        Question(
                            id = "ege_rus_2025_oral_t2_task1_q2",
                            text = "Сколько ножет у предмета",
                            imageUrl = "https://annihaus.ru/upload/iblock/561/56166c4717cd3ab25b0d15e598f82fb7.jpg",
                            answerType = AnswerType.SINGLE_CHOICE,
                            options = listOf("1", "2", "3", "4"),
                            correctAnswers = listOf("4")
                        )
                    )
                )
            )
        )
    )
)

/**
 * Пример mock данных для EГЭ Английский 2025
 */

val mockEgeTicketsEng2025 = listOf(
    Ticket(
        id = "ege_eng_2025_t1",
        number = 1,
        tasks = listOf(
            Task(
                id = "ege_eng_2025_t1_task1",
                number = 1,
                questions = listOf(
                    Question(
                        id = "ege_eng_2025_t1_task1_q1",
                        text = "Where I ?",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("ty", "te", "tyt", "ta"),
                        correctAnswers = listOf("tyt")
                    )
                )
            ),
            Task(
                id = "ege_eng_2025_t2_task2",
                number = 2,
                questions = listOf(
                    Question(
                        id = "ege_eng_2025_t2_task2_q1",
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
        id = "ege_eng_2025_oral_t2",
        number = 2,
        tasks = listOf(
            Task(
                id = "ege_eng_2025_oral_t2_task1",
                number = 1,
                questions = listOf(
                    Question(
                        id = "ege_eng_2025_oral_t2_task1_q1",
                        text = "Where W ?",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("w", "t", "y", "a"),
                        correctAnswers = listOf("w")
                    ),
                    Question(
                        id = "ege_eng_2025_oral_t2_task1_q2",
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