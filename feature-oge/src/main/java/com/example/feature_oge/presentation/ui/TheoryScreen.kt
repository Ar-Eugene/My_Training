package com.example.feature_oge.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.core.mock.mockSubjects
import com.example.core.ui.theme.BackgroundGradientBlue
import com.example.core.ui.theme.BackgroundGradientGreen
import com.example.feature_oge.presentation.ui.components.TopIconButtonAndText
import com.example.feature_oge.R

@Composable
fun TheoryScreen(
    subjectId: String,
    onBackClick: () -> Unit = {},
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
            .padding(horizontal = dimensionResource(com.example.core.R.dimen.padding_16dp))
    ) {
        // Заголовок
        TopIconButtonAndText(
            onClick = onBackClick,
            title = stringResource(R.string.theoretical_material_txt)
        )

    }
}


