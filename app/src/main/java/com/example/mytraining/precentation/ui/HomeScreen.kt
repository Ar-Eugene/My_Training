package com.example.mytraining.precentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.domain.models.ExamType
import com.example.core.ui.theme.BackgroundGradientGreen
import com.example.core.ui.theme.BackgroundGradientBlue
import com.example.feature_ege.presentation.ui.EgeScreen
import com.example.feature_oge.presentation.ui.OgeScreen
import com.example.mytraining.precentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val examTypeState = remember { mutableStateOf<ExamType?>(null) }
    val backgroundGradientColor = listOf(
        BackgroundGradientGreen, BackgroundGradientBlue
    )

    LaunchedEffect(Unit) {
        examTypeState.value = homeViewModel.getExamScreen()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = backgroundGradientColor))
    ) {
        // --- Кнопки выбора типа экзамена ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(com.example.core.R.dimen.padding_16dp)),
            horizontalArrangement = Arrangement.Start
        ) {
            SelectExamButton(
                text = "ОГЭ",
                selected = examTypeState.value == ExamType.OGE,
                onClick = {
                    homeViewModel.savaExamScreen(ExamType.OGE)
                    examTypeState.value = ExamType.OGE
                }
            )
            Spacer(modifier = Modifier.width(dimensionResource(com.example.core.R.dimen.padding_8dp)))
            SelectExamButton(
                text = "ЕГЭ",
                selected = examTypeState.value == ExamType.EGE,
                onClick = {
                    homeViewModel.savaExamScreen(ExamType.EGE)
                    examTypeState.value = ExamType.EGE
                }
            )
        }
        when (examTypeState.value) {
            ExamType.OGE -> OgeScreen()
            ExamType.EGE -> EgeScreen()
            null -> {
                // Показываем загрузку
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun SelectExamButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary else Color.LightGray,
            contentColor = if (selected) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(dimensionResource(com.example.core.R.dimen.padding_8dp))
    ) {
        Text(text)
    }
}
