package com.example.feature_profile.presentation.ui

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.feature_profile.R

/**
 * Корневой метод экрана
 */
@Composable
fun ProfileScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(
                start = dimensionResource(R.dimen.padding_16dp),
                end = dimensionResource(R.dimen.padding_16dp),
                top = dimensionResource(R.dimen.padding_24dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(R.dimen.image_size))
                .padding(dimensionResource(R.dimen.padding_8dp)),
            painter = painterResource(R.drawable.study_test)
        )
        Text(
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.padding_24dp)),
            textAlign = TextAlign.Center,
            text = "User",
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
                Toast.makeText(
                    context,
                    "Экран в разработке",
                    Toast.LENGTH_SHORT
                ).show()
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
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .padding(bottom = dimensionResource(R.dimen.padding_16dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_8dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.padding_16dp),
                    top = dimensionResource(R.dimen.padding_18dp),
                    bottom = dimensionResource(R.dimen.padding_18dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.padding_28dp)),
                painter = StartIconAction,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .padding(start = dimensionResource(R.dimen.padding_8dp))
                    .weight(1f),
                textAlign = TextAlign.Start,
                text = descriptionAction,
                style = MaterialTheme.typography.displayMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                modifier = Modifier
                    .padding(end = dimensionResource(R.dimen.padding_8dp))
                    .size(dimensionResource(R.dimen.padding_18dp)),
                painter = painterResource(com.example.core.R.drawable.arrow_forward_small_ic),
                contentDescription = null
            )
        }
    }
}
