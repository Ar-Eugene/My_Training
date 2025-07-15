package com.example.mytraining.precentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.BottomNavigationColor
import com.example.feature_favorites.presentation.ui.FavoriteScreen
import com.example.feature_profile.presentation.ui.ProfileScreen
import com.example.feature_profile.presentation.ui.TarifScreen
import com.example.mytraining.precentation.ui.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.Profile -> ProfileScreen() {
                        navController.navigate(it)
                    }

                    Destination.Favorites -> FavoriteScreen()
                    Destination.HOME -> HomeScreen()
                }
            }
        }
        composable("tarif-screen") { TarifScreen { navController.popBackStack() } }

    }
}

@Composable
fun NavigationBarExample(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = Destination.HOME
    // Отслеживаем текущий маршрут
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Определяем выбранный индекс на основе текущего маршрута
    val selectedDestination = Destination.entries.indexOfFirst { it.route == currentRoute }
        .takeIf { it != -1 } ?: startDestination.ordinal

    var showBottomBar = Destination.entries.any { it.route == currentRoute }
    Scaffold(
        modifier = modifier,
        bottomBar = {
            // нижняя чясть кода отвечает за анимацию появления и исчезновения нижней панели
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(
                    animationSpec = tween(500),
                    initialOffsetY = { fullHeight -> fullHeight },
                ),
                exit = slideOutVertically(
                    animationSpec = tween(500),
                    targetOffsetY = { fullHeight -> fullHeight },
                )
            ) {
                NavigationBar(
                    containerColor = BottomNavigationColor,
                    windowInsets = NavigationBarDefaults.windowInsets
                ) {
                    Destination.entries.forEachIndexed { index, destination ->
                        NavigationBarItem(
                            selected = selectedDestination == index,
                            onClick = {
                                navController.navigate(route = destination.route) {
                                    popUpTo(startDestination.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = destination.icon),
                                    contentDescription = destination.contentDescription,
                                    tint = if (selectedDestination == index) Color(0xFF324379) else Color(
                                        0xFF2C2C2C
                                    )
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(id = destination.label),
                                    color = if (selectedDestination == index) Color(0xFF324379) else Color(
                                        0xFF2C2C2C
                                    ) // или другой контрастный цвет,
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color(0xFFE0E5F2) // фон выделенного таба
                            )
                        )
                    }
                }
            }
        }
    ) { contentPadding ->
        AppNavHost(navController, startDestination, modifier = Modifier.padding(contentPadding))
    }
}