package com.example.feature_oge.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.mock.mockSubjects
import com.example.core.ui.theme.BottomNavigationColor

@Composable
fun TicketsScreen(
    subjectId: String,
    year: Int,
    modifier: Modifier = Modifier,
    onTicketClick: (String, Int, Int) -> Unit = { _, _, _ -> }
) {
    // Находим предмет и год
    val subject = mockSubjects.find { it.id == subjectId }
    val yearData = subject?.years?.find { it.year == year }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        // Заголовок
        Text(
            text = "Билеты: ${subject?.name ?: ""} $year",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Список билетов
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            yearData?.tickets?.let { tickets ->
                items(tickets) { ticket ->
                    TicketItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Билет ${ticket.number}",
                        onClick = {
                            onTicketClick(subjectId, year, ticket.number)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TicketItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .height(60.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = BottomNavigationColor.copy(alpha = 0.9f),
            contentColor = Color(0xFF324379)
        ),
        shape = RoundedCornerShape(dimensionResource(com.example.core.R.dimen.padding_8dp)),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
} 