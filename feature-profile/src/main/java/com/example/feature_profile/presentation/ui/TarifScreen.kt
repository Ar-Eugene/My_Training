package com.example.feature_profile.presentation.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.BackgroundGradientGreen
import com.example.core.ui.theme.BackgroundGradientBlue
import com.example.feature_profile.R


@Composable
fun TarifScreen(onNavigate: () -> Unit = {}) {
    val backgroundGradientColor = listOf(
        BackgroundGradientGreen, BackgroundGradientBlue
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = backgroundGradientColor))
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = dimensionResource(com.example.core.R.dimen.padding_16dp)),
            topBar = {
                TopAppBarState(onClick = onNavigate)
            },
            containerColor = Color.Transparent // убираем фон Scaffold, иначе перекроет градиент
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Spacer(modifier = Modifier.height(dimensionResource(com.example.core.R.dimen.padding_24dp)))

                ChoiceTarife(
                    iconRes = R.drawable.study_test,
                    titleRes = R.string.basic_tarif,
                    priceRes = R.string.price_basic_tarif,
                    descriptionRes = R.string.description_basic_tarif
                )

                ChoiceTarife(
                    iconRes = R.drawable.study_test,
                    titleRes = R.string.extended_tarif,
                    priceRes = R.string.price_extended_tarif,
                    descriptionRes = R.string.description_extended_tarif
                )

                ChoiceTarife(
                    iconRes = R.drawable.study_test,
                    titleRes = R.string.premium_tarif,
                    priceRes = R.string.price_premium_tarif,
                    descriptionRes = R.string.description_premium_tarif
                )
            }
        }
    }
}

/**
 * Отвечает за элементы верхней панели экрана
 */
@Composable
fun TopAppBarState(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Кнопка назад в левом углу
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.TopStart)

        ) {
            Icon(
                painter = painterResource(com.example.core.R.drawable.arrow_back_ic),
                contentDescription = "кнопка перехода на экран ProfileScreen"
            )
        }
        // Центральный контент (Image + Text)
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = dimensionResource(com.example.core.R.dimen.padding_28dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .size(98.dp)
                    .padding(dimensionResource(com.example.core.R.dimen.padding_8dp)),
                painter = painterResource(R.drawable.study_test),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.top_app_bar_premium_screen),
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}

/**
 * Отвечает за отображение card с элементами внутри него
 */
@Composable
fun ChoiceTarife(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    @StringRes titleRes: Int,
    @StringRes priceRes: Int,
    @StringRes descriptionRes: Int
) {
    var expanded by remember { mutableStateOf(false) }

    // отвечает за цвет при открытия описания тарифа
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer
    )

    Card(
        modifier = modifier
            .padding(bottom = dimensionResource(com.example.core.R.dimen.padding_16dp)),
        onClick = { expanded = !expanded }
    ) {
        // тут в Column анимация для плавного открытия описания тарифа
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .background(color = color)
        ) {
            AllCardElements(
                iconRes = iconRes,
                titleRes = titleRes,
                tarifRes = priceRes,
                descriptionRes = descriptionRes,
                expanded = expanded
            )
        }
    }
}

/**
 * Объеденяет все другие методы по работе с карточкой
 */

@Composable
fun AllCardElements(
    @DrawableRes iconRes: Int,
    @StringRes titleRes: Int,
    @StringRes tarifRes: Int,
    @StringRes descriptionRes: Int,
    expanded: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(com.example.core.R.dimen.padding_8dp))
    ) {
        TarifIcon(tarifIconRes = iconRes)
        TarifPrice(tarif = titleRes, tarifPrice = tarifRes)
        Spacer(Modifier.weight(1f))
        DescriptionItemButton(
            expanded = expanded
        )
    }
    if (expanded) {
        DescriptionTarif(descriptionTarif = descriptionRes)
    }
}

/**
 * Отвечает за клик по иконке раскрывающей описание тарифа
 */

@Composable
private fun DescriptionItemButton(
    expanded: Boolean
) {
    Icon(
        painter = if (expanded) {
            painterResource(com.example.core.R.drawable.expand_arrow_up_ic)
        } else {
            painterResource(com.example.core.R.drawable.expand_arrow_down_ic)
        },
        contentDescription = null,
        tint = MaterialTheme.colorScheme.secondary
    )

}

/**
 * Отвечает только за размеры и отступы иконки
 */
@Composable
fun TarifIcon(
    @DrawableRes tarifIconRes: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(dimensionResource(com.example.core.R.dimen.image_size_64p))
            .padding(dimensionResource(com.example.core.R.dimen.padding_8dp))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
        painter = painterResource(tarifIconRes),

        contentDescription = null
    )
}

/**
 * Отвечает только за текст тарифа и его стоимости
 */
@Composable
fun TarifPrice(
    @StringRes tarif: Int,
    @StringRes tarifPrice: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(tarif),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = dimensionResource(com.example.core.R.dimen.padding_8dp))
        )
        Text(
            text = stringResource(tarifPrice),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

/**
 * Отвечает только за данные которые лежат в развернутом виде
 */
@Composable
fun DescriptionTarif(
    @StringRes descriptionTarif: Int,
    //onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
    ) {
        Text(
            modifier = Modifier.padding(
                start = dimensionResource(com.example.core.R.dimen.padding_16dp),
                top = dimensionResource(com.example.core.R.dimen.padding_8dp),
                end = dimensionResource(com.example.core.R.dimen.padding_16dp)
            ),
            text = stringResource(descriptionTarif),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(dimensionResource(com.example.core.R.dimen.padding_8dp)))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    end = dimensionResource(com.example.core.R.dimen.padding_8dp),
                    bottom = dimensionResource(com.example.core.R.dimen.padding_8dp)
                ),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { /* действие */ },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(dimensionResource(com.example.core.R.dimen.padding_28dp)),
                    painter = painterResource(com.example.core.R.drawable.cart_ic),
                    contentDescription = null
                )
            }
        }
    }
}
