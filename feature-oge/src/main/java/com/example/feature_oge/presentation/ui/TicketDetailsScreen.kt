package com.example.feature_oge.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.domain.models.AnswerType
import com.example.core.mock.mockSubjects
import com.example.core.ui.theme.BottomNavigationColor

@Composable
fun TicketDetailsScreen(
    subjectId: String,
    year: Int,
    ticketNumber: Int,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    // Находим предмет, год и билет
    val subject = mockSubjects.find { it.id == subjectId }
    val yearData = subject?.years?.find { it.year == year }
    val ticket = yearData?.tickets?.find { it.number == ticketNumber }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        // Заголовок с кнопкой назад
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onBackClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BottomNavigationColor.copy(alpha = 0.9f),
                    contentColor = Color(0xFF324379)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("← Назад")
            }
            
            Text(
                text = "Билет $ticketNumber",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }
        
        Text(
            text = "${subject?.name ?: ""} $year",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Список заданий
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            ticket?.tasks?.let { tasks ->
                items(tasks) { task ->
                    TaskCard(task = task)
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: com.example.core.domain.models.Task) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BottomNavigationColor.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Заголовок задания
            Text(
                text = "Задание ${task.number}",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF324379),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            // Список вопросов
            task.questions.forEachIndexed { questionIndex, question ->
                QuestionItem(
                    question = question,
                    questionNumber = questionIndex + 1,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
fun QuestionItem(
    question: com.example.core.domain.models.Question,
    questionNumber: Int,
    modifier: Modifier = Modifier
) {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var selectedMultipleAnswers by remember { mutableStateOf(mutableSetOf<String>()) }
    
    Column(modifier = modifier) {
        // Текст вопроса
        Text(
            text = "Вопрос $questionNumber: ${question.text}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Варианты ответов в зависимости от типа
        when (question.answerType) {
            AnswerType.SINGLE_CHOICE -> {
                question.options?.forEachIndexed { index, option ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedAnswer == option,
                            onClick = { selectedAnswer = option }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
            }
            AnswerType.MULTIPLE_CHOICE -> {
                Text(
                    text = "Выберите несколько вариантов ответа:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                question.options?.forEach { option ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedMultipleAnswers.contains(option),
                            onCheckedChange = { checked ->
                                if (checked) {
                                    selectedMultipleAnswers.add(option)
                                } else {
                                    selectedMultipleAnswers.remove(option)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
            }
            AnswerType.TEXT_ANSWER -> {
                Text(
                    text = "Текстовый ответ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Введите ваш ответ в текстовое поле",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
        }
        
        // Правильный ответ (для демонстрации)
        if (selectedAnswer != null || selectedMultipleAnswers.isNotEmpty()) {
            Text(
                text = "Правильный ответ: ${question.correctAnswers.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Green,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
} 