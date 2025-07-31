package com.example.feature_favorites.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.example.core.ui.theme.BackgroundGradientGreen
import com.example.core.ui.theme.BackgroundGradientBlue

@Composable
fun FavoriteScreen(modifier: Modifier = Modifier) {
    val backgroundGradientColor = listOf(
        BackgroundGradientGreen, BackgroundGradientBlue
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(colors = backgroundGradientColor)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text("Favorite Screen")
    }
}