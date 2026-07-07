package com.example.core.data.firebase

object FirestorePathBuilder {

    fun ticketId(appSubjectId: String, year: Int, ticketNumber: Int): String =
        "${appSubjectId}_${year}_t$ticketNumber"

    fun taskId(ticketId: String, taskNumber: Int): String =
        "${ticketId}_task$taskNumber"

    fun parseTaskNumber(taskId: String): Int? =
        taskId.substringAfterLast("_task").toIntOrNull()

    fun parseTicketNumber(ticketId: String): Int? =
        ticketId.substringAfterLast("_t").toIntOrNull()
}
