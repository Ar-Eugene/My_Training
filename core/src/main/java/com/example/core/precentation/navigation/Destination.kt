package com.example.core.precentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.core.R

/**
 * Навигация для NavigationBar
 */

enum class Destination(
    val route: String,
    @StringRes
    val label: Int,
    @DrawableRes
    icone: Int
) {
    HOME("home_screen", R.string.home_label, R.drawable.home_label),
    Favorites("favorite_screen", R.string.favorite_label, R.drawable.favorite_label),
    Profile("profile_screen", R.string.profile_label, R.drawable.person_label)
}

