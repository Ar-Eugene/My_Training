package com.example.mytraining.precentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.domain.models.ExamType
import com.example.core.precentation.theme.Blue
import com.example.core.precentation.theme.White
import com.example.core.precentation.theme.WhiteSmoke
import com.example.feature_ege.presentation.ui.EgeScreen
import com.example.feature_oge.presentation.ui.OgeScreen
import com.example.mytraining.R
import com.example.mytraining.precentation.navigation.Routes
import com.example.mytraining.precentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},
) {
    val examTypeState = remember { mutableStateOf<ExamType?>(null) }
    examTypeState.value = homeViewModel.getExamScreen()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(dimensionResource(com.example.core.R.dimen.padding_16dp))
                .background(
                    color = White,
                    shape = RoundedCornerShape(dimensionResource(com.example.core.R.dimen.padding_28dp))
                )
        ) {
            IconButton(
                onClick = { onNavigate(Routes.SELECT_EXAM_FROM_HOME) },
                modifier = Modifier
                    .padding(dimensionResource(com.example.core.R.dimen.padding_6dp))
            ) {
                Icon(
                    painter = painterResource(R.drawable.change_exam),
                    contentDescription = "Сменить экзамен",
                    tint = Color(0xFFFBA061)
                )
            }
        }

        // Белая часть занимает фиксированную высоту от низа
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.72f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(WhiteSmoke)
        ) {
            when (examTypeState.value) {
                ExamType.OGE -> OgeScreen(
                    onSubjectClick = { subjectId ->
                        homeViewModel.navigateToYearSelection(subjectId, onNavigate)
                    }
                )

                ExamType.EGE -> EgeScreen()
                null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
