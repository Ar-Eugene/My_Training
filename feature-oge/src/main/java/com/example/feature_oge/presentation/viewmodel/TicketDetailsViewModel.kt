package com.example.feature_oge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.models.Question
import com.example.core.domain.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface TicketQuestionsUiState {
    data object Loading : TicketQuestionsUiState
    data class Success(val questions: List<Pair<Int, Question>>) : TicketQuestionsUiState
    data class Error(val message: String) : TicketQuestionsUiState
}

@HiltViewModel
class TicketDetailsViewModel @Inject constructor(
    private val questionRepository: QuestionRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<TicketQuestionsUiState>(TicketQuestionsUiState.Loading)
    val uiState: StateFlow<TicketQuestionsUiState> = _uiState.asStateFlow()

    fun loadQuestions(subjectId: String, year: Int, ticketNumber: Int) {
        viewModelScope.launch {
            _uiState.value = TicketQuestionsUiState.Loading

            _uiState.value = questionRepository
                .getQuestionsForTicket(subjectId, year, ticketNumber)
                .fold(
                    onSuccess = { questions ->
                        if (questions.isEmpty()) {
                            TicketQuestionsUiState.Error("Вопросы не найдены")
                        } else {
                            TicketQuestionsUiState.Success(questions)
                        }
                    },
                    onFailure = { error ->
                        TicketQuestionsUiState.Error(error.message ?: "Ошибка загрузки вопросов")
                    },
                )
        }
    }
}
