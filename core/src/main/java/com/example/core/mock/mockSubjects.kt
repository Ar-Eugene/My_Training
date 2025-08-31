package com.example.core.mock

import com.example.core.domain.models.ExamType
import com.example.core.domain.models.Subject
import com.example.core.domain.models.YearData

/**
 * Прдметы по годам и Экзаменам
 */

val mockSubjects = listOf(
    // ЕГЭ Русский язык
    Subject(
        id = "ege_rus",
        name = "Русский язык\nЕГЭ",
        examType = ExamType.EGE,
        years = listOf(
            YearData(
                year = 2024,
                tickets = mockEgeTicketsRus2024,
                oralPart = mockEgeOralPartRus2024
            ),
            YearData(
                year = 2025,
                tickets = mockEgeTicketsRus2025,
                oralPart = mockEgeOralPartRus2025
            )
        )
    ),
    // ЕГЭ Английский язык
    Subject(
        id = "ege_eng",
        name = "Английский язык ЕГЭ",
        examType = ExamType.EGE,
        years = listOf(
            YearData(
                year = 2024,
                tickets = mockEgeTicketsEng2024,
            ),
            YearData(
                year = 2025,
                tickets = mockEgeTicketsEng2025,
            )
        )
    ),

    // ОГЭ Английский язык
    Subject(
        id = "oge_english",
        name = "Английский язык ОГЭ",
        examType = ExamType.OGE,
        years = listOf(
            YearData(
                year = 2024,
                tickets = mockOgeTicketsEng2024,
                essays = mockOgeEssaysEng2024
            )
        )
    ),
    // ОГЭ Русский язык
    Subject(
        id = "oge_rus",
        name = "Русский язык\nОГЭ",
        examType = ExamType.OGE,
        years = listOf(
            YearData(
                year = 2025,
                tickets = mockOgeTicketsRus2025,
                essays = mockOgeEssaysRus2025
            )
        )
    )
)
