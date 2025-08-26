package com.example.feature_oge.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.BottomNavigationColor

@Composable
fun OgeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp) // Добавляем горизонтальные отступы
    ) {
        // Заголовок
        Text(
            text = "Выберите предмет",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Список предметов
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 колонки
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(ogeSubjects) { subject ->
                SelectSubject(
                    modifier = Modifier.fillMaxWidth(),
                    text = subject,
                    onClick = {
                        println("Выбран предмет: $subject")
                    }
                )
            }
        }
    }
}

// Список предметов ОГЭ для демонстрации
val ogeSubjects = listOf(
    "Русский язык",
    "Английский язык",
    "Математика",
    "Литература",
    "Русский язык",
    "Английский язык",
    "Математика",
    "Литература"
)
@Composable
fun SelectSubject(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .height(80.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = BottomNavigationColor.copy(alpha = 0.9f),
            contentColor = Color(0xFF324379)
        ),
        shape = RoundedCornerShape(dimensionResource(com.example.core.R.dimen.padding_8dp)),

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}