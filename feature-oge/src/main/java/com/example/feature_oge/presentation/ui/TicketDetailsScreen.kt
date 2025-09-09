package com.example.feature_oge.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.core.R
import com.example.core.domain.models.AnswerState
import com.example.core.domain.models.AnswerType
import com.example.core.domain.models.Question
import com.example.core.mock.mockSubjects
import com.example.core.ui.theme.BackgroundGradientBlue
import com.example.core.ui.theme.BackgroundGradientGreen
import com.example.core.ui.theme.BottomNavigationColor
import com.example.feature_oge.presentation.ui.components.AlertDialogExample
import com.example.feature_oge.presentation.ui.components.TopIconButtonAndText

@Composable
fun TicketDetailsScreen(
    subjectId: String,
    year: Int,
    ticketNumber: Int,
    onBackClick: () -> Unit = {},
) {
    val backgroundGradientColor = listOf(
        BackgroundGradientGreen, BackgroundGradientBlue
    )

    // диалоговое окно при выходе
    var showDialog by remember { mutableStateOf(false) }

    val subject = mockSubjects.find { it.id == subjectId }
    val yearData = subject?.years?.find { it.year == year }
    val ticket = yearData?.tickets?.find { it.number == ticketNumber }

    // Собираем все вопросы
    val allQuestions = ticket?.tasks?.flatMap { task ->
        task.questions.map { question -> task.number to question }
    } ?: emptyList()

    // Состояние для ответов
    val answers = remember { mutableStateMapOf<String, AnswerState>() }

    // Перехватываем системную кнопку "Назад"
    BackHandler {
        showDialog = true
    }
    // Диалог подтверждения
    if (showDialog) {
        AlertDialogExample(
            onDismissRequest = { showDialog = false },
            onConfirmation = {
                showDialog = false
                onBackClick()
            },
            dialogTitle = stringResource(com.example.feature_oge.R.string.confirm),
            dialogText = stringResource(com.example.feature_oge.R.string.are_you_sure),
            icon = Icons.Default.Warning
        )
    }

    Column(
        modifier = Modifier
            .background(brush = Brush.linearGradient(backgroundGradientColor))
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = dimensionResource(R.dimen.padding_16dp))
    ) {

        // Заголовок с кнопкой "Назад"
        TopIconButtonAndText(
            onClick = { showDialog = true }, // вместо прямого выхода
            title = "Билет № $ticketNumber"
        )

        // Горизонтальная прокрутка
        val pagerState = rememberPagerState(pageCount = { allQuestions.size })

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val (taskNumber, question) = allQuestions[page]
            val key = "${taskNumber}-${question.id}"

            TaskCardTicketScreen(
                taskNumber = taskNumber,
                questionNumber = page + 1,
                question = question,
                answerState = answers[key] ?: AnswerState(),
                onAnswerSelected = { answer ->
                    answers[key] = AnswerState(answer = answer)
                },
                onMultipleAnswersSelected = { selected ->
                    answers[key] = AnswerState(answer = selected)
                },
                onConfirm = {
                    answers[key] =
                        answers[key]?.copy(confirmed = true) ?: AnswerState(confirmed = true)
                }
            )
        }
    }
}

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
    val selectedAnswer = answerState.answer as? String
    val selectedMultipleAnswers = answerState.answer as? Set<String> ?: emptySet()
    val confirmed = answerState.confirmed

    // Определяем: отвечено или нет
    val isAnswered = when (question.answerType) {
        AnswerType.SINGLE_CHOICE -> selectedAnswer != null
        AnswerType.MULTIPLE_CHOICE -> confirmed
        AnswerType.TEXT_ANSWER -> confirmed
    }

    val isCorrect = when (question.answerType) {
        AnswerType.SINGLE_CHOICE ->
            selectedAnswer != null && selectedAnswer in question.correctAnswers

        AnswerType.MULTIPLE_CHOICE ->
            confirmed && selectedMultipleAnswers == question.correctAnswers.toSet()

        AnswerType.TEXT_ANSWER ->
            confirmed && selectedAnswer?.trim()
                ?.lowercase() in question.correctAnswers.map { it.lowercase() }
    }

    val backgroundColor = when {
        !isAnswered -> BottomNavigationColor
        isCorrect -> Color(0xFF4CAF50).copy(alpha = 0.9f)
        else -> Color(0xFFF44336).copy(alpha = 0.9f)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(dimensionResource(R.dimen.padding_8dp)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(dimensionResource(R.dimen.padding_12dp))
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_16dp))
        ) {
            Text(
                text = "Задание $taskNumber",
                style = MaterialTheme.typography.displayLarge,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_12dp))
            )
            QuestionItem(
                question = question,
                questionNumber = questionNumber,
                selectedAnswer = selectedAnswer,
                selectedMultipleAnswers = selectedMultipleAnswers,
                onAnswerSelected = onAnswerSelected,
                onMultipleAnswersSelected = onMultipleAnswersSelected,
                enabled = !isAnswered
            )

            // Кнопка подтверждения для MULTIPLE_CHOICE и TEXT_ANSWER
            if ((question.answerType == AnswerType.MULTIPLE_CHOICE || question.answerType == AnswerType.TEXT_ANSWER) && !confirmed) {
                Button(
                    onClick = { onConfirm() },
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_12dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BackgroundGradientGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text("Подтвердить")
                }
            }

            if (isAnswered) {
                Text(
                    text = "Правильный ответ: ${question.correctAnswers.joinToString(", ")}",
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_8dp))
                )
            }
        }
    }
}

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

            AnswerType.MULTIPLE_CHOICE -> {
                Text(
                    text = stringResource(com.example.feature_oge.R.string.select_some_answer),
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

            AnswerType.TEXT_ANSWER -> {
                // Состояния для текста и подтверждения
                var userInput by rememberSaveable { mutableStateOf(selectedAnswer ?: "") }

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

                AnswerTextField(
                    userInput = userInput,
                    onValueChange = { userInput = it },
                    enabled = enabled
                )

                if (enabled) {
                    onAnswerSelected(userInput)
                }
            }
        }
    }
}

/**
Отвечает за размещение и отображение вариантов ответов и за сам ответ
 */

@Composable
fun AnswerOptionItem(
    option: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean,
) {
    Row(
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_8dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_8dp)))
        Text(
            option,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}

/**
Отвечает за поле ввода при TEXT_ANSWER
 */
@Composable
fun AnswerTextField(
    userInput: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    placeholder: String = "Введите ответ",
) {
    TextField(
        value = userInput,
        onValueChange = { if (enabled) onValueChange(it) },
        placeholder = { Text(placeholder) },
        textStyle = MaterialTheme.typography.labelSmall,
        maxLines = 1,
        singleLine = true,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )
}

