package com.example.core.data.impl

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.domain.models.ExamType
import com.example.core.domain.repository.ExamPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "settings")

class ExamPreferencesRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    ExamPreferencesRepository {

    override suspend fun saveExamType(examType: ExamType) {
        context.dataStore.edit { pref ->
            pref[EXAM_TYPE_KEY] = examType.name
        }
    }

    override fun getExamType(): ExamType {
        return runBlocking {
            val savaExamType = context.dataStore.data
                .map { pref -> pref[EXAM_TYPE_KEY] }
                .firstOrNull()
            when (savaExamType) {
                ExamType.OGE.name -> ExamType.OGE
                ExamType.EGE.name -> ExamType.EGE
                else -> ExamType.OGE
            }
        }
    }

    override fun isExamTypeSelected(): Boolean {
        return runBlocking {
            val saved = context.dataStore.data
                .map { pref -> pref[EXAM_TYPE_KEY] }
                .firstOrNull()
            saved == ExamType.OGE.name || saved == ExamType.EGE.name
        }
    }

    companion object {
        private val EXAM_TYPE_KEY = stringPreferencesKey("exam_type_key")
    }
}