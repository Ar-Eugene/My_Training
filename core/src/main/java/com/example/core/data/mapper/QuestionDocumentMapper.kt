package com.example.core.data.mapper

import com.example.core.domain.models.AnswerType
import com.example.core.domain.models.Question
import com.google.firebase.firestore.DocumentSnapshot

object QuestionDocumentMapper {

    fun DocumentSnapshot.toQuestion(): Question? {

        val map = data ?: return null

        val questionId = map["id"] as? String ?: id
        val text = map["text"] as? String ?: return null

        val answerType = runCatching {
            AnswerType.valueOf(map["answerType"] as String)
        }.getOrNull() ?: return null

        val options = (map["options"] as? List<*>)?.filterIsInstance<String>()

        val correctAnswers = when (val value = map["correctAnswers"]) {
            is String -> listOf(value)
            is List<*> -> value.filterIsInstance<String>()
            else -> emptyList()
        }

        return Question(
            id = questionId,
            text = text,
            imageUrl = map["imageUrl"] as? String,
            answerType = answerType,
            options = options,
            correctAnswers = correctAnswers
        )
    }
}
