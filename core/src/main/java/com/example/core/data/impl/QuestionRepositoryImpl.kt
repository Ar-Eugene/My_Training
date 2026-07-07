package com.example.core.data.impl

import com.example.core.data.firebase.FirestorePathBuilder
import com.example.core.data.firebase.FirestoreSubjectMapper
import com.example.core.data.mapper.QuestionDocumentMapper.toQuestion
import com.example.core.domain.models.Question
import com.example.core.domain.models.QuestionContext
import com.example.core.domain.repository.QuestionRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : QuestionRepository {

    override suspend fun getQuestionsForTicket(
        subjectId: String,
        year: Int,
        ticketNumber: Int,
    ): Result<List<Pair<Int, Question>>> = runCatching {
        val ticketId = FirestorePathBuilder.ticketId(subjectId, year, ticketNumber)
        val ticketRef = ticketDocument(subjectId, year, ticketId)

        val tasksSnapshot = ticketRef.collection(TASKS).get().await()

        tasksSnapshot.documents
            .flatMap { taskDoc ->
                val taskNumber = FirestorePathBuilder.parseTaskNumber(taskDoc.id) ?: return@flatMap emptyList()
                val questionsSnapshot = taskDoc.reference.collection(QUESTIONS).get().await()
                questionsSnapshot.documents.mapNotNull { questionDoc ->
                    questionDoc.toQuestion()?.let { taskNumber to it }
                }
            }
            .sortedWith(compareBy({ it.first }, { it.second.id }))
    }

    override suspend fun getQuestionsForTaskNumber(
        subjectId: String,
        year: Int,
        taskNumber: Int,
    ): Result<List<QuestionContext>> = runCatching {
        val ticketsSnapshot = ticketsCollection(subjectId, year).get().await()

        ticketsSnapshot.documents.flatMap { ticketDoc ->
            val ticketNumber = FirestorePathBuilder.parseTicketNumber(ticketDoc.id)
                ?: return@flatMap emptyList()

            val taskId = FirestorePathBuilder.taskId(ticketDoc.id, taskNumber)
            val questionsSnapshot = ticketDoc.reference
                .collection(TASKS)
                .document(taskId)
                .collection(QUESTIONS)
                .get()
                .await()

            questionsSnapshot.documents.mapNotNull { questionDoc ->
                questionDoc.toQuestion()?.let { question ->
                    QuestionContext(
                        question = question,
                        ticketNumber = ticketNumber,
                        taskNumber = taskNumber,
                    )
                }
            }
        }
    }

    private fun ticketsCollection(subjectId: String, year: Int) =
        firestore.collection(SUBJECTS)
            .document(FirestoreSubjectMapper.toFirestoreSubjectId(subjectId))
            .collection(YEARS)
            .document(year.toString())
            .collection(TICKETS)

    private fun ticketDocument(subjectId: String, year: Int, ticketId: String) =
        ticketsCollection(subjectId, year).document(ticketId)

    companion object {
        private const val SUBJECTS = "subjects"
        private const val YEARS = "years"
        private const val TICKETS = "tickets"
        private const val TASKS = "tasks"
        private const val QUESTIONS = "questions"
    }
}
