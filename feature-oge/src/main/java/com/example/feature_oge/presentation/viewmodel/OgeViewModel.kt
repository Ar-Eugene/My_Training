package com.example.feature_oge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OgeViewModel : ViewModel() {
    
    private val _favoriteSubjects = MutableStateFlow<Set<String>>(emptySet())
    val favoriteSubjects: StateFlow<Set<String>> = _favoriteSubjects.asStateFlow()
    
    fun toggleFavorite(subjectId: String) {
        val currentFavorites = _favoriteSubjects.value.toMutableSet()
        if (currentFavorites.contains(subjectId)) {
            currentFavorites.remove(subjectId)
        } else {
            currentFavorites.add(subjectId)
        }
        _favoriteSubjects.value = currentFavorites
    }
    
    fun isFavorite(subjectId: String): Boolean {
        return _favoriteSubjects.value.contains(subjectId)
    }
}
