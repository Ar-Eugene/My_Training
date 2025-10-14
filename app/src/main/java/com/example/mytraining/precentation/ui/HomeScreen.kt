package com.example.mytraining.precentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.core.precentation.theme.BackgroundGradientBlue
import com.example.core.precentation.theme.BackgroundGradientGreen
import com.example.core.precentation.theme.BottomNavigationColor
import com.example.feature_ege.presentation.ui.EgeScreen
import com.example.feature_oge.presentation.ui.OgeScreen
import com.example.mytraining.precentation.navigation.Routes
import com.example.mytraining.precentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},
) {
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
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(com.example.core.R.dimen.padding_16dp)),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { onNavigate(Routes.SELECT_EXAM_FROM_HOME) },
                shape = RoundedCornerShape(dimensionResource(com.example.core.R.dimen.padding_8dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BottomNavigationColor,
                    contentColor = Color.Black
                )
            ) {
                Text("Сменить экзамен")
            }
        }
        when (examTypeState.value) {
            ExamType.OGE -> OgeScreen(
                onSubjectClick = { subjectId ->
                    homeViewModel.navigateToYearSelection(subjectId, onNavigate)
                }
            )

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
