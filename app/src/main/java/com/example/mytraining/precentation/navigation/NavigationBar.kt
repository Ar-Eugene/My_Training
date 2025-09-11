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
import com.example.feature_oge.presentation.ui.AllQuestionsScreen
import com.example.feature_oge.presentation.ui.SelectionTrainingSectionScreen
import com.example.feature_oge.presentation.ui.TheoryScreen
import com.example.feature_oge.presentation.ui.TicketDetailsScreen
import com.example.feature_oge.presentation.ui.TicketsScreen
import com.example.feature_oge.presentation.ui.YearSelectionScreen
import com.example.feature_profile.presentation.ui.ProfileScreen
import com.example.feature_profile.presentation.ui.TarifScreen
import com.example.mytraining.precentation.ui.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
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
                    Destination.HOME -> HomeScreen(
                        onNavigate = { route ->
                            navController.navigate(route)
                        }
                    )
                }
            }
        }
        composable("tarif-screen") { TarifScreen { navController.popBackStack() } }

        // переход на экран выбора года
        composable("year-selection/{subjectId}") { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
            YearSelectionScreen(
                subjectId = subjectId,
                onYearClick = { subjectId, year ->
                    navController.navigate("training-section/$subjectId/$year")
                },
                onNavigate = { navController.popBackStack() }
            )
        }

        // переход на экран выбора раздела обучения
        composable("training-section/{subjectId}/{year}") { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
            val year = backStackEntry.arguments?.getString("year")?.toIntOrNull() ?: 0
            SelectionTrainingSectionScreen(
                subjectId = subjectId,
                year = year,
                onSectionClick = { subjectId, year, section ->
                    when (section) {
                        "tickets" -> navController.navigate("tickets/$subjectId/$year/tickets")
                        "all-questions" -> navController.navigate("all-questions/$subjectId/$year")
                        "theory" -> navController.navigate("theory/$subjectId/$year")
                    }
                },
                onNavigate = { navController.popBackStack() }
            )
        }

        // переход на экран выбора выбора билета
        composable("tickets/{subjectId}/{year}/{training-section}") { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
            val year = backStackEntry.arguments?.getString("year")?.toIntOrNull() ?: 0
            val trainingSection = backStackEntry.arguments?.getString("training-section") ?: ""
            TicketsScreen(
                subjectId = subjectId,
                year = year,
                trainingSection = trainingSection,
                onTicketClick = { subjectId, year, trainingSection, ticketNumber ->
                    navController.navigate("ticket-details/$subjectId/$year/$trainingSection/$ticketNumber")
                },
                onNavigate = { navController.popBackStack() }
            )
        }

        // переход на экран списка вопросов из билета
        composable("ticket-details/{subjectId}/{year}/{training-section}/{ticketNumber}") { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
            val year = backStackEntry.arguments?.getString("year")?.toIntOrNull() ?: 0
            val ticketNumber =
                backStackEntry.arguments?.getString("ticketNumber")?.toIntOrNull() ?: 0
            TicketDetailsScreen(
                subjectId = subjectId,
                year = year,
                ticketNumber = ticketNumber,
                onBackClick = { navController.popBackStack() }
            )
        }

        // переход на экран всех вопросов
        composable("all-questions/{subjectId}/{year}") { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
            val year = backStackEntry.arguments?.getString("year")?.toIntOrNull() ?: 0
            AllQuestionsScreen(
                subjectId = subjectId,
                year = year,
                onBackClick = { navController.popBackStack() }
            )
        }

        // переход на экран теории
        composable("theory/{subjectId}/{year}") { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
            //val year = backStackEntry.arguments?.getString("year")?.toIntOrNull() ?: 0
            TheoryScreen(
                subjectId = subjectId,
                onBackClick = { navController.popBackStack() }
            )
        }

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