package com.example.feature_oge.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.R
import com.example.core.domain.models.AnswerState
import com.example.core.domain.models.AnswerType
import com.example.core.domain.models.Question
import com.example.core.precentation.components.AlertDialogExample
import com.example.core.precentation.components.TaskCardTicketScreen
import com.example.core.precentation.components.TopIconButtonAndText
import com.example.core.precentation.theme.BackgroundGradientBlue
import com.example.core.precentation.theme.BackgroundGradientGreen
import com.example.feature_oge.presentation.viewmodel.TicketDetailsViewModel
import com.example.feature_oge.presentation.viewmodel.TicketQuestionsUiState
import kotlinx.coroutines.launch

@Composable
fun TicketDetailsScreen(
    subjectId: String,
    year: Int,
    ticketNumber: Int,
    onBackClick: () -> Unit = {},
    viewModel: TicketDetailsViewModel = hiltViewModel(),
) {
    val backgroundGradientColor = listOf(
        BackgroundGradientGreen, BackgroundGradientBlue
    )

    LaunchedEffect(subjectId, year, ticketNumber) {
        viewModel.loadQuestions(subjectId, year, ticketNumber)
    }

    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        TicketQuestionsUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(backgroundGradientColor)),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        is TicketQuestionsUiState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(backgroundGradientColor))
                    .statusBarsPadding()
                    .padding(16.dp),
            ) {
                TopIconButtonAndText(onClick = onBackClick, title = "Билет № $ticketNumber")
                Text(
                    text = state.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 32.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }

        is TicketQuestionsUiState.Success -> {
            TicketDetailsContent(
                ticketNumber = ticketNumber,
                allQuestions = state.questions,
                onBackClick = onBackClick,
            )
        }
    }
}

@Composable
private fun TicketDetailsContent(
    ticketNumber: Int,
    allQuestions: List<Pair<Int, Question>>,
    onBackClick: () -> Unit,
) {
    val backgroundGradientColor = listOf(
        BackgroundGradientGreen, BackgroundGradientBlue
    )

    var showDialog by remember { mutableStateOf(false) }

    val answers = remember { mutableStateMapOf<String, AnswerState>() }

    val allQuestionsAnswered by remember {
        derivedStateOf {
            allQuestions.all { (taskNumber, question) ->
                val key = "${taskNumber}-${question.id}"
                val answerState = answers[key]
                when (question.answerType) {
                    AnswerType.SINGLE_CHOICE -> answerState?.answer != null
                    AnswerType.MULTIPLE_CHOICE -> answerState?.confirmed == true
                    AnswerType.TEXT_ANSWER -> answerState?.confirmed == true &&
                            answerState.answer is String &&
                            (answerState.answer as String).isNotBlank()
                }
            }
        }
    }

    val correctAnswersCount by remember {
        derivedStateOf {
            allQuestions.count { (taskNumber, question) ->
                val key = "${taskNumber}-${question.id}"
                val answerState = answers[key]

                when (question.answerType) {
                    AnswerType.SINGLE_CHOICE ->
                        (answerState?.answer as? String)?.let { it in question.correctAnswers } == true

                    AnswerType.MULTIPLE_CHOICE ->
                        answerState?.confirmed == true &&
                                (answerState.answer as? Set<String>) == question.correctAnswers.toSet()

                    AnswerType.TEXT_ANSWER ->
                        answerState?.confirmed == true &&
                                (answerState.answer as? String)?.trim()?.lowercase() in
                                question.correctAnswers.map { it.lowercase() }

                    else -> false
                }
            }
        }
    }

    BackHandler {
        if (!allQuestionsAnswered) {
            showDialog = true
        } else {
            onBackClick()
        }
    }

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
        TopIconButtonAndText(
            onClick = {
                if (!allQuestionsAnswered) {
                    showDialog = true
                } else {
                    onBackClick()
                }
            },
            title = "Билет № $ticketNumber"
        )

        val pagerState = rememberPagerState(pageCount = { allQuestions.size + 1 })
        val scope = rememberCoroutineScope()

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            if (page < allQuestions.size) {
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
            } else {
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
                                answers.clear()
                                scope.launch {
                                    pagerState.scrollToPage(0)
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
