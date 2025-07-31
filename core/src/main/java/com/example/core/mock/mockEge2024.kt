package com.example.core.mock

import com.example.core.domain.models.AnswerType
import com.example.core.domain.models.OralPart
import com.example.core.domain.models.Question
import com.example.core.domain.models.Task
import com.example.core.domain.models.Ticket

/**
 * Пример mock данных для ЕГЭ Русский язык 2024
 */

val mockEgeTicketsRus2024 = listOf(

    /**
     * Билет 1
     */

    Ticket(
        id = "ege_rus_2024_t1",
        number = 1,
        tasks = listOf(
            Task(
                id = "ege_rus_2024_t1_task1",
                number = 1,
                questions = listOf(
                    Question(
                        id = "ege_rus_2024_t1_task1_q1",
                        text = "Поставьте в правильную форму: нет (500) стульев",
                        answerType = AnswerType.TEXT_ANSWER,
                        correctAnswers = listOf("пятисот")
                    ),
                    Question(
                        id = "ege_rus_2024_t1_task1_q2",
                        text = "Вопрос 2",
                        answerType = AnswerType.TEXT_ANSWER,
                        correctAnswers = listOf("Вопрос 2")
                    )

                )
            ),
            Task(
                id = "ege_rus_2024_t1_task2",
                number = 2,
                questions = listOf(
                    Question(
                        id = "ege_rus_2024_t1_task2_q1",
                        text = "Выберете слово с чередующейся гласной",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("заря", "окно", "дверь", "камета"),
                        correctAnswers = listOf("заря")
                    )
                )
            )
        )
    ),

    /**
     * Билет 2
     */

    Ticket(
        id = "ege_rus_2024_t2",
        number = 2,
        tasks = listOf(
            Task(
                id = "ege_rus_2024_t2_task1",
                number = 1,
                questions = listOf(
                    Question(
                        id = "ege_rus_2024_t2_task1_q1",
                        text = "Поставьте в правильную форму: нет (600) стульев",
                        answerType = AnswerType.TEXT_ANSWER,
                        correctAnswers = listOf("шестисот")
                    )
                )
            ),
            Task(
                id = "ege_rus_2024_t2_task2",
                number = 2,
                questions = listOf(
                    Question(
                        id = "ege_rus_2024_t2_task2_q1",
                        text = "Слово с мягким звуком",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("нос", "конь", "слон", "зуб"),
                        correctAnswers = listOf("конь")
                    ),
                    Question(
                        id = "ege_rus_2024_t2_task2_q2",
                        text = "Вопрос 2",
                        answerType = AnswerType.TEXT_ANSWER,
                        correctAnswers = listOf("Вопрос 2")
                    )
                )
            )
        )
    )
)

// Пример mock данных для устной части ЕГЭ
val mockEgeOralPartRus2024 = OralPart(
    id = "ege_rus_2024_oral",
    tickets = listOf(
        Ticket(
            id = "ege_rus_2024_oral_t1",
            number = 1,
            tasks = listOf(
                Task(
                    id = "ege_rus_2024_oral_t1_task1",
                    number = 1,
                    questions = listOf(
                        Question(
                            id = "ege_rus_2024_oral_t1_task1_q1",
                            text = "Объясните теорему Пифагора",
                            answerType = AnswerType.TEXT_ANSWER,
                            correctAnswers = listOf("В прямоугольном треугольнике квадрат гипотенузы равен сумме квадратов катетов")
                        )
                    )
                )
            )
        ),
        Ticket(
            id = "ege_rus_2024_oral_t2",
            number = 2,
            tasks = listOf(
                Task(
                    id = "ege_rus_2024_oral_t2_task1",
                    number = 1,
                    questions = listOf(
                        Question(
                            id = "ege_rus_2024_oral_t2_task1_q1",
                            text = "Объясните что-то",
                            answerType = AnswerType.TEXT_ANSWER,
                            correctAnswers = listOf("Что-то")
                        )
                    )
                )
            )
        )
    )
)

/**
 * Пример mock данных для EГЭ Английский 2024
 */

val mockEgeTicketsEng2024 = listOf(
    Ticket(
        id = "ege_eng_2024_t1",
        number = 1,
        tasks = listOf(
            Task(
                id = "ege_eng_2024_t1_task1",
                number = 1,
                questions = listOf(
                    Question(
                        id = "ege_eng_2024_t1_task1_q1",
                        text = "Where I ?",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("ty", "te", "tyt", "ta"),
                        correctAnswers = listOf("tyt")
                    )
                )
            ),
            Task(
                id = "ege_eng_2024_t2_task2",
                number = 2,
                questions = listOf(
                    Question(
                        id = "ege_eng_2024_t2_task2_q1",
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
        id = "ege_eng_2024_oral_t2",
        number = 2,
        tasks = listOf(
            Task(
                id = "ege_eng_2024_oral_t2_task1",
                number = 1,
                questions = listOf(
                    Question(
                        id = "ege_eng_2024_oral_t2_task1_q1",
                        text = "Where W ?",
                        answerType = AnswerType.SINGLE_CHOICE,
                        options = listOf("w", "t", "y", "a"),
                        correctAnswers = listOf("w")
                    ),
                    Question(
                        id = "ege_eng_2024_oral_t2_task1_q2",
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




