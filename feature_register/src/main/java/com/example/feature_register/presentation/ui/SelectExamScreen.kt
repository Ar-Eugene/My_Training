package com.example.feature_register.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.domain.models.ExamType
import com.example.core.precentation.theme.Blue
import com.example.core.precentation.theme.White
import com.example.core.precentation.theme.WhiteSmoke
import com.example.feature_register.R
import com.example.feature_register.presentation.viewmodel.SelectExamViewModel

@Composable
fun SelectExamScreen(
    onOgeClick: () -> Unit,
    onEgeClick: () -> Unit,
    viewModel: SelectExamViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // ВЕРХНЯЯ часть
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(com.example.core.R.dimen.padding_16dp))
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.school_objects_img),
                    contentDescription = null
                )

                Text(
                    text = stringResource(R.string.select_exam),
                    style = MaterialTheme.typography.displayLarge.copy(color = White)
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(com.example.core.R.dimen.padding_8dp)))

            // Нижняя белая часть
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(WhiteSmoke)
                    .padding(dimensionResource(com.example.core.R.dimen.padding_24dp))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.saveExamType(ExamType.OGE)
                            onOgeClick()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp),
                        shape = RoundedCornerShape(20.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = dimensionResource(com.example.core.R.dimen.padding_16dp),
                            pressedElevation = dimensionResource(com.example.core.R.dimen.padding_16dp)
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue,
                            contentColor = White
                        )
                    ) {
                        Text(
                            text = stringResource(com.example.core.R.string.oge),
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = White,
                                fontSize = 21.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(com.example.core.R.dimen.padding_24dp)))

                    Button(
                        onClick = {
                            viewModel.saveExamType(ExamType.EGE)
                            onEgeClick()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp),
                        shape = RoundedCornerShape(20.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = dimensionResource(com.example.core.R.dimen.padding_16dp),
                            pressedElevation = dimensionResource(com.example.core.R.dimen.padding_16dp)
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue,
                            contentColor = White
                        )
                    ) {
                        Text(
                            text = stringResource(com.example.core.R.string.ege),
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = White,
                                fontSize = 21.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
