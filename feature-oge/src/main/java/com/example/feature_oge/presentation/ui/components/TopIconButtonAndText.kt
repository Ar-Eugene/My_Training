package com.example.feature_oge.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core.R

/**
 * Общий компонент для кнопки назад и заголовка
 */
@Composable
fun TopIconButtonAndText(
    onClick: () -> Unit,
    title: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.padding_16dp))
    ) {
        // Кнопка назад
        IconButton(
            onClick = onClick,
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_back_ic),
                contentDescription = "Назад",
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.padding_12dp))
            )
        }

        Text(
            modifier = Modifier
                .padding(start = 43.dp)
                .align(Alignment.TopStart),
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            softWrap = true
        )
    }
}
