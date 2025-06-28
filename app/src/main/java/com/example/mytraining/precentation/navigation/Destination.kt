package com.example.mytraining.precentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.mytraining.R


/**
 * Навигация для NavigationBar
 */

enum class Destination(
    val route: String,
    @StringRes
    val label: Int,
    @DrawableRes
    val icon: Int,
    val contentDescription: String
) {
    HOME("home_screen", R.string.home_label, R.drawable.home_label,"home_screen"),
    Favorites("favorite_screen", R.string.favorite_label, R.drawable.favorite_label,"favorite_screen"),
    Profile("profile_screen", R.string.profile_label, R.drawable.person_label,"profile_screen")
}

