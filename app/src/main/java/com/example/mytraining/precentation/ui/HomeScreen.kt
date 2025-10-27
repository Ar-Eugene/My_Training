package com.example.mytraining.precentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.domain.models.ExamType
import com.example.core.precentation.theme.Blue
import com.example.core.precentation.theme.LightBlue
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

        Column(
            modifier = Modifier
                .padding(dimensionResource(com.example.core.R.dimen.padding_16dp)),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.hello_user),
                style = MaterialTheme.typography.labelSmall.copy(color = White)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Пользователь",
                    style = MaterialTheme.typography.displayLarge.copy(
                        color = White,
                        fontSize = 27.sp
                    )
                )
                Box(
                    modifier = Modifier
                        .background(
                            color = LightBlue,
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
                            tint = White
                        )
                    }
                }
            }
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = White,
                ),
                modifier = Modifier
                    .padding(top = dimensionResource(com.example.core.R.dimen.padding_16dp))
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    // Синий круг с иконкой - слева по центру
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = dimensionResource(com.example.core.R.dimen.padding_16dp))
                            .background(
                                color = Blue,
                                shape = CircleShape
                            )
                            .size(dimensionResource(com.example.core.R.dimen.padding_48dp))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.change_exam),
                            contentDescription = "Описание иконки",
                            tint = White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }


                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 80.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        // Тип экзамена
                        Text(
                            text = "Экзамен: ",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall
                        )
                        // Прогресс обучения
                        Text(
                            text = stringResource(R.string.your_progress),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier.padding(top = dimensionResource(com.example.core.R.dimen.padding_8dp))
                        )
                    }

                    // Простой круг в правом углу
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(top = 16.dp, end = 16.dp)
                            .background(
                                color = Blue, // Замените на нужный цвет
                                shape = CircleShape
                            )
                            .size(24.dp) // Размер круга
                    )
                }
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
            /**
             * Текст о выборе предмета
             */

            Column {
                Text(
                    modifier = Modifier.padding(dimensionResource(com.example.core.R.dimen.padding_16dp)),
                    text = stringResource(R.string.choose_subject_txt),
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 33.sp),

                    )
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
}
