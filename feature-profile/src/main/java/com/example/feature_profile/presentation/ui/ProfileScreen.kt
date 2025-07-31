package com.example.feature_profile.presentation.ui

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.core.ui.theme.BackgroundGradientGreen
import com.example.core.ui.theme.BackgroundGradientBlue
import com.example.core.ui.theme.CardBackgroundGradientGreen
import com.example.core.ui.theme.CardBackgroundGradientBrown
import com.example.feature_profile.R

/**
 * Корневой метод экрана
 */
@Composable
fun ProfileScreen(onNavigate: (String) -> Unit = {}) {
    val context = LocalContext.current
    val backgroundGradientColor = listOf(
        BackgroundGradientGreen, BackgroundGradientBlue
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(colors = backgroundGradientColor)
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(
                start = dimensionResource(com.example.core.R.dimen.padding_16dp),
                end = dimensionResource(com.example.core.R.dimen.padding_16dp),
                top = dimensionResource(com.example.core.R.dimen.padding_24dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_profile_image)).value,
            modifier = Modifier
                .size(180.dp),
            iterations = LottieConstants.IterateForever // Бесконечное повторение
        )
        Text(
            modifier = Modifier
                .padding(bottom = dimensionResource(com.example.core.R.dimen.padding_24dp)),
            textAlign = TextAlign.Center,
            text = "Пользователь",
            style = MaterialTheme.typography.displayLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        //Написать в поддержку
        CardАctionSelection(
            StartIconAction = painterResource(R.drawable.chat_support_ic),
            descriptionAction = stringResource(R.string.write_support),
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:codecraft27@mail.ru")
                    putExtra(Intent.EXTRA_SUBJECT, R.string.share_progect)
                }
                context.startActivity(intent)
            }
        )

        // Поделиться приложением
        CardАctionSelection(
            StartIconAction = painterResource(R.drawable.share_ic),
            descriptionAction = stringResource(R.string.share_progect),
            onClick = {
                Toast.makeText(
                    context,
                    "Функция будет доступна после релиза",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
//            onClick = {val intent = Intent(Intent.ACTION_SEND).apply {
//                type = "text/plain"
//                putExtra(Intent.EXTRA_TEXT, )
//            }
//                context.startActivity(Intent.createChooser(intent, "Поделиться приложением"))}


        // Оформить премиум
        CardАctionSelection(
            StartIconAction = painterResource(R.drawable.premium_ic),
            descriptionAction = stringResource(R.string.sign_up_for_premium),
            onClick = {
                onNavigate("tarif-screen")
            }
        )
    }

}

/**
 *
 * Метод отвечающий за CardАction
 */
@Composable
fun CardАctionSelection(
    onClick: () -> Unit,
    StartIconAction: Painter,
    descriptionAction: String
) {
    val backgroundGradientColor = listOf(
        CardBackgroundGradientGreen, CardBackgroundGradientBrown,
    )
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .padding(bottom = dimensionResource(com.example.core.R.dimen.padding_16dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(com.example.core.R.dimen.padding_8dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.horizontalGradient(colors = backgroundGradientColor))
                .padding(
                    start = dimensionResource(com.example.core.R.dimen.padding_16dp),
                    top = dimensionResource(com.example.core.R.dimen.padding_18dp),
                    bottom = dimensionResource(com.example.core.R.dimen.padding_18dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(dimensionResource(com.example.core.R.dimen.padding_28dp)),
                painter = StartIconAction,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .padding(start = dimensionResource(com.example.core.R.dimen.padding_8dp))
                    .weight(1f),
                textAlign = TextAlign.Start,
                text = descriptionAction,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                modifier = Modifier
                    .padding(end = dimensionResource(com.example.core.R.dimen.padding_8dp))
                    .size(dimensionResource(com.example.core.R.dimen.padding_18dp)),
                painter = painterResource(com.example.core.R.drawable.arrow_forward_small_ic),
                contentDescription = null
            )
        }
    }
}