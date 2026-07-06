package com.example.feature_oge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.models.QuestionContext
import com.example.core.domain.repository.QuestionRepository
import com.example.core.mock.mockSubjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface QuestionsUiState {
    data object Loading : QuestionsUiState
    data class Success(val questions: List<QuestionContext>) : QuestionsUiState
    data class Error(val message: String) : QuestionsUiState
}

@HiltViewModel
class AllQuestionsViewModel @Inject constructor(
    private val questionRepository: QuestionRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<QuestionsUiState>(QuestionsUiState.Loading)
    val uiState: StateFlow<QuestionsUiState> = _uiState.asStateFlow()

    val subjectName: String?
        get() = _subjectId?.let { id -> mockSubjects.find { it.id == id }?.name }

    private var _subjectId: String? = null
    private var _year: Int? = null

    fun loadQuestions(subjectId: String, year: Int, taskId: String) {
        _subjectId = subjectId
        _year = year

        val taskNumber = when (taskId) {
            "all-questions-task1" -> 1
            "all-questions-task2" -> 2
            "all-questions-task3" -> 3
            else -> null
        }

        viewModelScope.launch {
            _uiState.value = QuestionsUiState.Loading

            val result = if (taskNumber != null) {
                questionRepository.getQuestionsForTaskNumber(subjectId, year, taskNumber)
            } else {
                Result.failure(IllegalArgumentException("Неизвестный тип задания"))
            }

            _uiState.value = result.fold(
                onSuccess = { questions ->
                    if (questions.isEmpty()) {
                        QuestionsUiState.Error("Вопросы не найдены")
                    } else {
                        QuestionsUiState.Success(questions.shuffled())
                    }
                },
                onFailure = { error ->
                    QuestionsUiState.Error(error.message ?: "Ошибка загрузки вопросов")
                },
            )
        }
    }

    fun year(): Int = _year ?: 0
}
