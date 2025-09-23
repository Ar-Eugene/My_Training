package com.example.core.precentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core.R
import com.example.core.domain.models.AnswerType
import com.example.core.domain.models.Question

/**
 * Отвечает за тип вопроса
 */

@Composable
fun QuestionItem(
    question: Question,
    questionNumber: Int,
    selectedAnswer: String?,
    selectedMultipleAnswers: Set<String>,
    onAnswerSelected: (String) -> Unit,
    onMultipleAnswersSelected: (Set<String>) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(modifier = modifier) {
        Text(
            text = "Вопрос $questionNumber: ${question.text}",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_8dp))
        )

        when (question.answerType) {
            AnswerType.SINGLE_CHOICE -> {
                SingleChoiceQuestion(
                    question = question,
                    selectedAnswer = selectedAnswer,
                    onAnswerSelected = onAnswerSelected,
                    enabled = enabled
                )
            }

            AnswerType.MULTIPLE_CHOICE -> {
                MultiChoiceQuestion(
                    question = question,
                    selectedMultipleAnswers = selectedMultipleAnswers,
                    onMultipleAnswersSelected = onMultipleAnswersSelected,
                    enabled = enabled
                )
            }
            AnswerType.TEXT_ANSWER -> {
                TextAnswerQuestion(
                    question = question,
                    selectedAnswer = selectedAnswer,
                    onAnswerSelected = onAnswerSelected,
                    enabled = enabled
                )
            }
        }
    }
}

/**
 * Логика при вопросе где допустим только 1 вариант ответа
 */
@Composable
private fun SingleChoiceQuestion(
    question: Question,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        question.options?.forEach { option ->
            val checked = selectedAnswer == option
            AnswerOptionItem(
                option = option,
                checked = checked,
                onCheckedChange = { newChecked ->
                    if (enabled && newChecked) {
                        onAnswerSelected(option)
                    }
                },
                enabled = enabled
            )
        }
    }
}

/**
 * Логика при вопросе где допустимы несколько вариантов ответов
 */

@Composable
private fun MultiChoiceQuestion(
    question: Question,
    selectedMultipleAnswers: Set<String>,
    onMultipleAnswersSelected: (Set<String>) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.select_some_answer),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_8dp))
        )
        question.options?.forEach { option ->
            val checked = selectedMultipleAnswers.contains(option)
            AnswerOptionItem(
                option = option,
                checked = checked,
                onCheckedChange = { newChecked ->
                    if (enabled) {
                        val updated = selectedMultipleAnswers.toMutableSet()
                        if (newChecked) updated.add(option) else updated.remove(option)
                        onMultipleAnswersSelected(updated)
                    }
                },
                enabled = enabled
            )
        }
    }
}

/**
 * Логика при вопросе где пользователь сам должен написать ответ
 */

@Composable
private fun TextAnswerQuestion(
    question: Question,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
// Картинка (если есть)
        question.imageUrl?.let { url ->
            AsyncImage(
                model = url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_8dp)))
                    .padding(bottom = dimensionResource(R.dimen.padding_12dp)),
                contentScale = ContentScale.Crop
            )
        }
        // Состояния для текста и подтверждения
        var userInput by rememberSaveable { mutableStateOf(selectedAnswer ?: "") }

        AnswerTextField(
            userInput = userInput,
            onValueChange = {
                userInput = it
                // Передаем ответ только если текст не пустой
                if (it.isNotBlank()) {
                    onAnswerSelected(it)
                }
            },
            enabled = enabled
        )
        if (enabled) {
            onAnswerSelected(userInput)
        }
    }
}