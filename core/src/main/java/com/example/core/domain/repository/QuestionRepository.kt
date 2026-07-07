package com.example.core.domain.repository

import com.example.core.domain.models.Question
import com.example.core.domain.models.QuestionContext

interface QuestionRepository {

    suspend fun getQuestionsForTicket(
        subjectId: String,
        year: Int,
        ticketNumber: Int,
    ): Result<List<Pair<Int, Question>>>

    suspend fun getQuestionsForTaskNumber(
        subjectId: String,
        year: Int,
        taskNumber: Int,
    ): Result<List<QuestionContext>>
}
