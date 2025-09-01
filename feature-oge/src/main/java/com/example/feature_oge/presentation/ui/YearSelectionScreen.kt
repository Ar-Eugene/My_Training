package com.example.feature_oge.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.mock.mockSubjects
import com.example.core.ui.theme.BackgroundGradientBlue
import com.example.core.ui.theme.BackgroundGradientGreen
import com.example.core.ui.theme.CardBackgroundGradientBrown

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
            .padding(horizontal = 16.dp)

    ) {
        TopIconButtonAndText(onClick = onNavigate, title = "Выберите год: ${subject?.name ?: ""}")

        // Список годов
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            subject?.years?.let { years ->
                items(years) { yearData ->
                    SelectYear(
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
/**
 *
 * Метод отвечающий за кнопку назад и текст рядом
 */
@Composable
fun TopIconButtonAndText(
    onClick: () -> Unit,
    title: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        // Кнопка назад
        IconButton(
            onClick = onClick,
            modifier = Modifier.align(Alignment.TopStart) // строго в верхнем левом углу
        ) {
            Icon(
                painter = painterResource(com.example.core.R.drawable.arrow_back_ic),
                contentDescription = "Назад"
            )
        }

        Text(
            modifier = Modifier
                .padding(start = 43.dp)
                .align(Alignment.TopStart),
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            softWrap = true
        )
    }
}


/**
 *
 * Метод отвечающий за выбор года
 */
@Composable
fun SelectYear(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    val backgroundGradientColor = listOf(
        CardBackgroundGradientBrown,
        BackgroundGradientBlue
    )
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .padding(bottom = dimensionResource(com.example.core.R.dimen.padding_16dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(com.example.core.R.dimen.padding_8dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.horizontalGradient(colors = backgroundGradientColor))
                .padding(
                    start = dimensionResource(com.example.core.R.dimen.padding_16dp),
                    top = dimensionResource(com.example.core.R.dimen.padding_18dp),
                    bottom = dimensionResource(com.example.core.R.dimen.padding_18dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = dimensionResource(com.example.core.R.dimen.padding_8dp))
                    .weight(1f),
                textAlign = TextAlign.Center,
                text = text,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                modifier = Modifier
                    .padding(end = dimensionResource(com.example.core.R.dimen.padding_8dp))
                    .size(dimensionResource(com.example.core.R.dimen.padding_18dp)),
                painter = painterResource(com.example.core.R.drawable.arrow_forward_small_ic),
                contentDescription = null
            )
        }
    }
}