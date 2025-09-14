package com.example.feature_oge.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.style.TextAlign
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
import kotlinx.coroutines.launch

@Composable
fun AllQuestionsScreen(
    subjectId: String,
    year: Int,
    onBackClick: () -> Unit = {},
) {
    val backgroundGradientColor = listOf(
        BackgroundGradientGreen, BackgroundGradientBlue
    )

    // диалоговое окно при выходе
    var showDialog by remember { mutableStateOf(false) }

    // Находим предмет по ID
    val subject = mockSubjects.find { it.id == subjectId }
    val yearData = subject?.years?.find { it.year == year }

    // Получаем все вопросы из всех билетов и сохраняем перемешанный порядок
    val allQuestions = remember(subject) {
        yearData?.tickets?.flatMap { ticket ->
            ticket.tasks.flatMap { task ->
                task.questions.map { question ->
                    QuestionWithContext(
                        question = question,
                        ticketNumber = ticket.number,
                        taskNumber = task.number
                    )
                }
            }
        }?.shuffled() ?: emptyList()
    }

    // Состояние для ответов
    val answers = remember { mutableStateMapOf<String, AnswerState>() }

    // Проверяем, все ли вопросы отвечены
    val allQuestionsAnswered by remember {
        derivedStateOf {
            allQuestions.all { questionWithContext ->
                val key =
                    "${questionWithContext.ticketNumber}-${questionWithContext.taskNumber}-${questionWithContext.question.id}"
                val answerState = answers[key]
                when (questionWithContext.question.answerType) {
                    AnswerType.SINGLE_CHOICE -> answerState?.answer != null
                    AnswerType.MULTIPLE_CHOICE -> answerState?.confirmed == true
                    AnswerType.TEXT_ANSWER -> answerState?.confirmed == true &&
                            answerState.answer is String &&
                            (answerState.answer as String).isNotBlank()
                }
            }
        }
    }

    // Считаем количество правильных ответов
    val correctAnswersCount by remember {
        derivedStateOf {
            allQuestions.count { questionWithContext ->
                val key =
                    "${questionWithContext.ticketNumber}-${questionWithContext.taskNumber}-${questionWithContext.question.id}"
                val answerState = answers[key]

                when (questionWithContext.question.answerType) {
                    AnswerType.SINGLE_CHOICE ->
                        (answerState?.answer as? String)?.let { it in questionWithContext.question.correctAnswers } == true

                    AnswerType.MULTIPLE_CHOICE ->
                        answerState?.confirmed == true &&
                                (answerState.answer as? Set<String>) == questionWithContext.question.correctAnswers.toSet()

                    AnswerType.TEXT_ANSWER ->
                        answerState?.confirmed == true &&
                                (answerState.answer as? String)?.trim()?.lowercase() in
                                questionWithContext.question.correctAnswers.map { it.lowercase() }

                    else -> false
                }
            }
        }
    }


    // Перехватываем системную кнопку "Назад"
    BackHandler {
        // Показываем диалог только если не все вопросы отвечены
        if (!allQuestionsAnswered) {
            showDialog = true
        } else {
            onBackClick() // Если все отвечено, просто выходим
        }
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
            .padding(horizontal = dimensionResource(R.dimen.padding_16dp))
    ) {
        // Заголовок
        TopIconButtonAndText(
            onClick = {
                // Показываем диалог только если не все вопросы отвечены
                if (!allQuestionsAnswered) {
                    showDialog = true
                } else {
                    onBackClick()// Если все отвечено, просто выходим
                }
            }, // вместо прямого выхода
            title = "Все вопросы: ${subject?.name ?: ""} $year"
        )

        // Горизонтальная прокрутка
        val pagerState = rememberPagerState(pageCount = { allQuestions.size + 1 })
        val scope = rememberCoroutineScope()

        if (allQuestions.isEmpty()) {
            Text(
                text = "Вопросы не найдены",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        } else {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                if (page < allQuestions.size) {
                    // обычные вопросы
                    val questionWithContext = allQuestions[page]
                    val key =
                        "${questionWithContext.ticketNumber}-${questionWithContext.taskNumber}-${questionWithContext.question.id}"

                    TaskCardTicketScreenр(
                        taskNumber = questionWithContext.taskNumber,
                        questionNumber = page + 1,
                        question = questionWithContext.question,
                        answerState = answers[key] ?: AnswerState(),
                        onAnswerSelected = { answer ->
                            answers[key] = AnswerState(answer = answer)
                        },
                        onMultipleAnswersSelected = { selected ->
                            answers[key] = AnswerState(answer = selected)
                        },
                        onConfirm = {
                            answers[key] =
                                answers[key]?.copy(confirmed = true)
                                    ?: AnswerState(confirmed = true)
                        }
                    )
                } else {
                    // последняя страница – результат
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (allQuestionsAnswered) {
                            Text(
                                text = "Вы ответили правильно на $correctAnswersCount из ${allQuestions.size} вопросов",
                                style = MaterialTheme.typography.displayLarge,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                color = if (correctAnswersCount >= allQuestions.size / 2) Color(
                                    0xFF4CAF50
                                ) else Color(0xFFF44336),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Button(
                                onClick = {
                                    answers.clear() // сброс ответов
                                    scope.launch {
                                        pagerState.scrollToPage(0) // мгновенно кидаем на первую страницу
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BackgroundGradientGreen,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Пройти ещё раз")
                            }
                        } else {
                            Text(
                                text = "Ответьте на все вопросы, чтобы увидеть результат",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Модель вопроса с контекстом (билет и задание)
 */
data class QuestionWithContext(
    val question: Question,
    val ticketNumber: Int,
    val taskNumber: Int,
)

@Composable
fun TaskCardTicketScreenр(
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

    // Проверка на валидность ответа для кнопки подтверждения
    val isAnswerValid = when (question.answerType) {
        AnswerType.MULTIPLE_CHOICE -> selectedMultipleAnswers.isNotEmpty()
        AnswerType.TEXT_ANSWER -> !selectedAnswer.isNullOrBlank()
        else -> true // Для SINGLE_CHOICE всегда true, так как там нет кнопки подтверждения
    }

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
            QuestionItemр(
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
                    onClick = {
                        if (isAnswerValid) {
                            onConfirm()
                        }
                    },
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_12dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isAnswerValid) BackgroundGradientGreen else Color.Gray,
                        contentColor = Color.White
                    ),
                    enabled = isAnswerValid // Делаем кнопку неактивной при невалидном ответе
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
fun QuestionItemр(
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
                    AnswerOptionItemр(
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
                    AnswerOptionItemр(
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

                AnswerTextFieldр(
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
    }
}

/**
Отвечает за размещение и отображение вариантов ответов и за сам ответ
 */

@Composable
fun AnswerOptionItemр(
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
fun AnswerTextFieldр(
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