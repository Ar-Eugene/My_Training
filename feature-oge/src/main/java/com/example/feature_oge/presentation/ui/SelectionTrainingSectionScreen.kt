package com.example.feature_oge.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import com.example.core.R
import com.example.core.domain.models.TrainingSection
import com.example.core.mock.mockSubjects
import com.example.core.ui.theme.BackgroundGradientBlue
import com.example.core.ui.theme.BackgroundGradientGreen
import com.example.feature_oge.presentation.ui.components.RowContent
import com.example.feature_oge.presentation.ui.components.TopIconButtonAndText

@Composable
fun SelectionTrainingSectionScreen(
    subjectId: String,
    year: Int,
    onSectionClick: (String, Int, String) -> Unit = { _, _, _ -> },
    onNavigate: () -> Unit = {},
) {
    val backgroundGradientColor = listOf(
        BackgroundGradientGreen, BackgroundGradientBlue
    )

    // Находим предмет по ID
    val subject = mockSubjects.find { it.id == subjectId }

    // Создаем список разделов обучения
    val trainingSections = listOf(
        TrainingSection("tickets", "Задачи по билетам"),
        TrainingSection("all-questions", "Изучить все вопросы"),
        TrainingSection("theory", "Теория")
    )

    Column(
        modifier = Modifier
            .background(brush = Brush.linearGradient(backgroundGradientColor))
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = dimensionResource(R.dimen.padding_16dp))
    ) {
        TopIconButtonAndText(
            onClick = onNavigate,
            title = "${subject?.name ?: ""} $year"
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_12dp)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_6dp)),
            modifier = Modifier.fillMaxSize()
        ) {
            items(trainingSections) { section ->
                TrainingSectionCard(
                    modifier = Modifier.fillMaxWidth(),
                    section = section,
                    onClick = {
                        onSectionClick(subjectId, year, section.id)
                    }
                )
            }
        }
    }
}


/**
 * Метод отвечающий за карточку раздела обучения
 */
@Composable
private fun TrainingSectionCard(
    modifier: Modifier = Modifier,
    section: TrainingSection,
    onClick: () -> Unit,
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier
            .padding(bottom = dimensionResource(R.dimen.padding_16dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.padding_8dp)
        ),
    ) {
        RowContent(text = section.title)
    }
}