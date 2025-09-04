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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import com.example.core.R
import com.example.core.mock.mockSubjects
import com.example.core.ui.theme.BackgroundGradientBlue
import com.example.core.ui.theme.BackgroundGradientGreen
import com.example.feature_oge.presentation.ui.components.ComponentSelectTransition
import com.example.feature_oge.presentation.ui.components.TopIconButtonAndText

@Composable
fun YearSelectionScreen(
    subjectId: String,
    modifier: Modifier = Modifier,
    onYearClick: (String, Int) -> Unit = { _, _ -> },
    onNavigate: () -> Unit = {},
) {
    val backgroundGradientColor = listOf(
        BackgroundGradientGreen, BackgroundGradientBlue
    )
    // Находим предмет по ID
    val subject = mockSubjects.find { it.id == subjectId }

    Column(
        modifier = Modifier
            .background(brush = Brush.linearGradient(backgroundGradientColor))
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = dimensionResource(R.dimen.padding_16dp))

    ) {
        TopIconButtonAndText(onClick = onNavigate, title = "${subject?.name ?: ""}")

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_12dp)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_6dp)),
            modifier = Modifier.fillMaxSize()
        ) {
            subject?.years?.let { years ->
                items(years) { yearData ->
                    ComponentSelectTransition(
                        modifier = Modifier.fillMaxWidth(),
                        text = yearData.year.toString(),
                        onClick = {
                            onYearClick(subjectId, yearData.year)
                        }
                    )
                }
            }
        }
    }
}
