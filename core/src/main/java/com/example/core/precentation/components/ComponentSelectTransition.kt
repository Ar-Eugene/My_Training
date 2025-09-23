package com.example.core.precentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.core.precentation.theme.BackgroundGradientBlue
import com.example.core.precentation.theme.CardBackgroundGradientBrown
import com.example.core.R

/**
 * Отвечает за кнопку перехода к следующему экрану
 */
@Composable
fun ComponentSelectTransition(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier
            .padding(bottom = dimensionResource(R.dimen.padding_16dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_8dp)),
    ) {
        RowContent(text = text)
    }
}

/**
 * Отвечает за размещение элементов в кнопке
 */
@Composable
fun RowContent(text: String) {
    val backgroundGradientColor = listOf(
        CardBackgroundGradientBrown,
        BackgroundGradientBlue
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = Brush.horizontalGradient(colors = backgroundGradientColor))
            .padding(
                start = dimensionResource(R.dimen.padding_16dp),
                top = dimensionResource(R.dimen.padding_18dp),
                bottom = dimensionResource(R.dimen.padding_18dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContentText(
            text = text, modifier = Modifier
                .padding(start = dimensionResource(R.dimen.padding_8dp))
                .weight(1f)
        )
        ContentIcon()
    }
}

/**
 * Отвечает за размещение текста в кнопке
 */
@Composable
private fun ContentText(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        textAlign = TextAlign.Center,
        text = text,
        style = MaterialTheme.typography.labelSmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

/**
 * Отвечает за размещение иконки в кнопке
 */
@Composable
private fun ContentIcon() {
    Icon(
        modifier = Modifier
            .padding(end = dimensionResource(R.dimen.padding_8dp))
            .size(dimensionResource(R.dimen.padding_18dp)),
        painter = painterResource(R.drawable.arrow_forward_small_ic),
        contentDescription = null
    )
}