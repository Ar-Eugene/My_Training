package com.example.core.data.firebase

object FirestoreSubjectMapper {

    private val appToFirestore = mapOf(
        "oge_rus" to "russian_oge",
    )

    fun toFirestoreSubjectId(appSubjectId: String): String =
        appToFirestore[appSubjectId] ?: appSubjectId
}
