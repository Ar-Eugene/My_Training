package com.example.mytraining.precentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.interactor.AuthInteractor
import com.example.core.domain.interactor.ExamInteractor
import com.example.core.domain.models.ExamType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val examInteractor: ExamInteractor,
    private val authInteractor: AuthInteractor,
) : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {

            authInteractor
                .getCurrentUser()
                .onSuccess { user ->
                    _userName.value = user.userName
                }
        }
    }

    fun getExamScreen(): ExamType {
        return examInteractor.getExamScreen()
    }

    fun navigateToYearSelection(
        subjectId: String,
        onNavigate: (String) -> Unit,
    ) {
        onNavigate("year-selection/$subjectId")
    }

    fun getExamTypeDisplayName(): String {
        return when (getExamScreen()) {
            ExamType.OGE -> "ОГЭ"
            ExamType.EGE -> "ЕГЭ"
        }
    }
}
