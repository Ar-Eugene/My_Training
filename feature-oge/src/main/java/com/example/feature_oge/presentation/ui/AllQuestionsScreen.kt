package com.example.feature_oge.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.domain.models.Question
import com.example.core.mock.mockSubjects
import com.example.core.ui.theme.BackgroundGradientBlue
import com.example.core.ui.theme.BackgroundGradientGreen
import com.example.core.ui.theme.BottomNavigationColor
import com.example.feature_oge.presentation.ui.components.TopIconButtonAndText

@Composable
fun AllQuestionsScreen(
    subjectId: String,
    year: Int,
    onBackClick: () -> Unit = {}
) {
    val backgroundGradientColor = listOf(
        BackgroundGradientGreen, BackgroundGradientBlue
    )
    
    // Находим предмет по ID
    val subject = mockSubjects.find { it.id == subjectId }
    val yearData = subject?.years?.find { it.year == year }
    
    // Получаем все вопросы из всех билетов
    val allQuestions = yearData?.tickets?.flatMap { ticket ->
        ticket.tasks.flatMap { task ->
            task.questions.map { question ->
                QuestionWithContext(
                    question = question,
                    ticketNumber = ticket.number,
                    taskNumber = task.number
                )
            }
        }
    } ?: emptyList()
    
    Column(
        modifier = Modifier
            .background(brush = Brush.linearGradient(backgroundGradientColor))
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        TopIconButtonAndText(
            onClick = onBackClick, 
            title = "Все вопросы: ${subject?.name ?: ""} $year"
        )

        // Заголовок
        Text(
            text = "Все вопросы по предмету",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Счетчик вопросов
        Text(
            text = "Всего вопросов: ${allQuestions.size}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Список всех вопросов
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            if (allQuestions.isEmpty()) {
                item {
                    Text(
                        text = "Вопросы не найдены",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 32.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                itemsIndexed(allQuestions) { index, questionWithContext ->
                    QuestionCard(
                        questionWithContext = questionWithContext,
                        questionNumber = index + 1
                    )
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
    val taskNumber: Int
)

/**
 * Карточка вопроса
 */
@Composable
fun QuestionCard(
    questionWithContext: QuestionWithContext,
    questionNumber: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BottomNavigationColor.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Заголовок с номером вопроса и контекстом
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Вопрос $questionNumber",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF324379),
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "Билет ${questionWithContext.ticketNumber}, Задание ${questionWithContext.taskNumber}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.Gray.copy(alpha = 0.3f)
            )
            
            // Текст вопроса
            Text(
                text = questionWithContext.question.text,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            // Тип ответа
            Text(
                text = "Тип ответа: ${getAnswerTypeText(questionWithContext.question.answerType)}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Варианты ответов (если есть)
            questionWithContext.question.options?.let { options ->
                Text(
                    text = "Варианты ответов:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                
                options.forEachIndexed { index, option ->
                    Text(
                        text = "${index + 1}. $option",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 16.dp, bottom = 2.dp)
                    )
                }
            }
            
            // Правильные ответы
            Text(
                text = "Правильный ответ: ${questionWithContext.question.correctAnswers.joinToString(", ")}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/**
 * Получить текст типа ответа
 */
@Composable
fun getAnswerTypeText(answerType: com.example.core.domain.models.AnswerType): String {
    return when (answerType) {
        com.example.core.domain.models.AnswerType.SINGLE_CHOICE -> "Выбор одного ответа"
        com.example.core.domain.models.AnswerType.MULTIPLE_CHOICE -> "Выбор нескольких ответов"
        com.example.core.domain.models.AnswerType.TEXT_ANSWER -> "Текстовый ответ"
    }
}
