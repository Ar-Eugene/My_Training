package com.example.feature_oge.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core.mock.mockSubjects
import com.example.core.ui.theme.BackgroundGradientBlue
import com.example.core.ui.theme.BackgroundGradientGreen
import com.example.feature_oge.presentation.ui.components.ComponentSelectTransition
import com.example.feature_oge.presentation.ui.components.TopIconButtonAndText

@Composable
fun TicketsScreen(
    subjectId: String,
    year: Int,
    trainingSection: String,
    modifier: Modifier = Modifier,
    onTicketClick: (String, Int, String, Int) -> Unit = { _, _, _, _ -> },
    onNavigate: () -> Unit = {},
) {

    val backgroundGradientColor = listOf(
        BackgroundGradientGreen, BackgroundGradientBlue
    )

    // Находим предмет и год
    val subject = mockSubjects.find { it.id == subjectId }
    val yearData = subject?.years?.find { it.year == year }

    Column(
        modifier = Modifier
            .background(brush = Brush.linearGradient(backgroundGradientColor))
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = dimensionResource(R.dimen.padding_16dp))
    ) {

        // Заголовок
        TopIconButtonAndText(
            onClick = onNavigate,
            title = "Билеты: ${subject?.name ?: ""} $year"
        )

        // Список билетов
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            yearData?.tickets?.let { tickets ->
                items(tickets) { ticket ->
                    ComponentSelectTransition(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Билет ${ticket.number}",
                        onClick = {
                            onTicketClick(subjectId, year, trainingSection, ticket.number)
                        }
                    )
                }
            }
        }
    }
}