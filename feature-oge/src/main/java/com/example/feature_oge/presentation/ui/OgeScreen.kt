package com.example.feature_oge.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.domain.models.ExamType
import com.example.core.domain.models.Subject
import com.example.core.mock.mockSubjects
import com.example.core.precentation.theme.BackgroundGradientBlue
import com.example.core.precentation.theme.CardBackgroundGradientBrown
import com.example.feature_oge.R
import com.example.feature_oge.presentation.viewmodel.OgeViewModel

@Composable
fun OgeScreen(
    modifier: Modifier = Modifier,
    onSubjectClick: (String) -> Unit = {},
    viewModel: OgeViewModel = hiltViewModel(),
) {
    // Фильтруем только предметы ОГЭ
    val ogeSubjects = mockSubjects.filter { it.examType == ExamType.OGE }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        // список предметов
        ListSubject(
            ogeSubjects = ogeSubjects,
            onSubjectClick = onSubjectClick,
            viewModel = viewModel
        )

    }
}

/**
 * Кнопка предмета
 */
@Composable
fun SelectSubject(
    modifier: Modifier = Modifier,
    text: String,
    subjectId: String,
    onClick: () -> Unit,
    viewModel: OgeViewModel,
) {
    val favoriteSubjects by viewModel.favoriteSubjects.collectAsState()
    val isFavorite = favoriteSubjects.contains(subjectId)

    val backgroundGradientColor = listOf(
        CardBackgroundGradientBrown,
        BackgroundGradientBlue
    )
    Button(
        onClick = onClick,
        modifier = modifier
            .height(80.dp)
            .background(
                brush = Brush.verticalGradient(
                    backgroundGradientColor
                ),
                shape = RoundedCornerShape(dimensionResource(com.example.core.R.dimen.padding_8dp))
            ),
        shape = RoundedCornerShape(dimensionResource(com.example.core.R.dimen.padding_8dp)),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, // фон убираем
            contentColor = Color.White
        )
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Icon(
                modifier = Modifier
                    .size(dimensionResource(com.example.core.R.dimen.padding_28dp))
                    .clickable {
                        viewModel.toggleFavorite(subjectId)
                    },
                painter = painterResource(R.drawable.add_favorites),
                tint = if (isFavorite) Color.Red else Color.White,
                contentDescription = null
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(com.example.core.R.dimen.padding_8dp))
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

/**
 * список предметов
 */
@Composable
fun ListSubject(
    ogeSubjects: List<Subject>,
    onSubjectClick: (String) -> Unit,
    viewModel: OgeViewModel,
) {
    // Список предметов
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(ogeSubjects) { subject ->
            SelectSubject(
                modifier = Modifier.fillMaxWidth(),
                text = subject.name,
                subjectId = subject.id,
                onClick = {
                    onSubjectClick(subject.id)
                },
                viewModel = viewModel
            )
        }
    }
}