package com.example.core.precentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.example.core.R
import com.example.core.domain.models.AnswerLogic
import com.example.core.domain.models.AnswerState
import com.example.core.domain.models.AnswerType
import com.example.core.domain.models.Question
import com.example.core.precentation.theme.BackgroundGradientGreen
import com.example.core.precentation.theme.BottomNavigationColor

/**
 * Отвечает за карточку где содержится полноценный вопрос с названием,
 * вариантами ответов и сами ответы
 */

@Composable
fun TaskCardTicketScreen(
    taskNumber: Int,
    questionNumber: Int,
    question: Question,
    answerState: AnswerState,
    onAnswerSelected: (String) -> Unit,
    onMultipleAnswersSelected: (Set<String>) -> Unit,
    onConfirm: () -> Unit,
) {
    val answerLogic = CheckingResponseLogic(question, answerState)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(dimensionResource(R.dimen.padding_8dp)),
        colors = CardDefaults.cardColors(containerColor = answerLogic.backgroundColor),
        shape = RoundedCornerShape(dimensionResource(R.dimen.padding_12dp))
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_16dp))
        ) {
            TaskHeader(taskNumber = taskNumber)

            QuestionItem(
                question = question,
                questionNumber = questionNumber,
                selectedAnswer = answerState.answer as? String,
                selectedMultipleAnswers = answerState.answer as? Set<String> ?: emptySet(),
                onAnswerSelected = onAnswerSelected,
                onMultipleAnswersSelected = onMultipleAnswersSelected,
                enabled = !answerLogic.isAnswered
            )

            ConfirmButton(
                questionType = question.answerType,
                confirmed = answerState.confirmed,
                isAnswerValid = answerLogic.isAnswerValid,
                onConfirm = onConfirm
            )

            CorrectAnswerText(
                isAnswered = answerLogic.isAnswered,
                correctAnswers = question.correctAnswers
            )
        }
    }
}

/**
 * Логика проверки ответов
 */
@Composable
fun CheckingResponseLogic(
    question: Question,
    answerState: AnswerState,
): AnswerLogic {
    val selectedAnswer = answerState.answer as? String
    val selectedMultipleAnswers = answerState.answer as? Set<String> ?: emptySet()
    val confirmed = answerState.confirmed

    return remember(question, answerState) {
        val isAnswered = when (question.answerType) {
            AnswerType.SINGLE_CHOICE -> selectedAnswer != null
            AnswerType.MULTIPLE_CHOICE -> confirmed
            AnswerType.TEXT_ANSWER -> confirmed
            else -> false
        }

        val isCorrect = when (question.answerType) {
            AnswerType.SINGLE_CHOICE ->
                selectedAnswer != null && selectedAnswer in question.correctAnswers

            AnswerType.MULTIPLE_CHOICE ->
                confirmed && selectedMultipleAnswers == question.correctAnswers.toSet()

            AnswerType.TEXT_ANSWER ->
                confirmed && selectedAnswer?.trim()?.lowercase() in
                        question.correctAnswers.map { it.lowercase() }

            else -> false
        }

        AnswerLogic(
            isAnswerValid = when (question.answerType) {
                AnswerType.MULTIPLE_CHOICE -> selectedMultipleAnswers.isNotEmpty()
                AnswerType.TEXT_ANSWER -> !selectedAnswer.isNullOrBlank()
                else -> true
            },
            isAnswered = isAnswered,
            isCorrect = isCorrect,
            backgroundColor = when {
                !isAnswered -> BottomNavigationColor
                isCorrect -> Color(0xFF4CAF50).copy(alpha = 0.9f)
                else -> Color(0xFFF44336).copy(alpha = 0.9f)
            }
        )
    }
}

/**
 * Кнопка подтверждения ответа при MULTIPLE_CHOICE и TEXT_ANSWER
 */
@Composable
fun ConfirmButton(
    questionType: AnswerType,
    confirmed: Boolean,
    isAnswerValid: Boolean,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if ((questionType == AnswerType.MULTIPLE_CHOICE || questionType == AnswerType.TEXT_ANSWER) && !confirmed) {
        Button(
            onClick = {
                if (isAnswerValid) {
                    onConfirm()
                }
            },
            modifier = modifier.padding(top = dimensionResource(R.dimen.padding_12dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isAnswerValid) BackgroundGradientGreen else Color.Gray,
                contentColor = Color.White
            ),
            enabled = isAnswerValid
        ) {
            Text("Подтвердить")
        }
    }
}

/**
 * Компонент отвечающий за отображение правильного ответа
 */
@Composable
fun CorrectAnswerText(
    isAnswered: Boolean,
    correctAnswers: List<String>,
    modifier: Modifier = Modifier,
) {
    if (isAnswered) {
        Text(
            text = "Правильный ответ: ${correctAnswers.joinToString(", ")}",
            style = MaterialTheme.typography.displayLarge,
            fontSize = 24.sp,
            modifier = modifier.padding(top = dimensionResource(R.dimen.padding_8dp))
        )
    }
}

/**
 * Заголовок задания
 */
@Composable
fun TaskHeader(
    taskNumber: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Задание $taskNumber",
        style = MaterialTheme.typography.displayLarge,
        fontSize = 24.sp,
        modifier = modifier.padding(bottom = dimensionResource(R.dimen.padding_12dp))
    )
}