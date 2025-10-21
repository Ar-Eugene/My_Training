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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.precentation.theme.White
import com.example.core.precentation.ui.TaskSelectionTicketScreen
import com.example.core.precentation.ui.TicketsScreen
import com.example.core.precentation.ui.YearSelectionScreen
import com.example.feature_favorites.presentation.ui.FavoriteScreen
import com.example.feature_oge.presentation.ui.AllQuestionsScreen
import com.example.feature_oge.presentation.ui.SelectionTrainingSectionScreen
import com.example.feature_oge.presentation.ui.TheoryScreen
import com.example.feature_oge.presentation.ui.TicketDetailsScreen
import com.example.feature_profile.presentation.ui.ProfileScreen
import com.example.feature_profile.presentation.ui.TarifScreen
import com.example.feature_register.presentation.ui.AuthorizationScreen
import com.example.feature_register.presentation.ui.RegisterScreen
import com.example.feature_register.presentation.ui.SelectExamScreen
import com.example.mytraining.precentation.ui.HomeScreen
import com.example.mytraining.precentation.viewmodel.AuthGateViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Routes.AUTHORIZATION,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.AUTHORIZATION) {
            val gateViewModel: AuthGateViewModel = hiltViewModel()
            AuthorizationScreen(
                onLoginClick = {
                    if (gateViewModel.isExamTypeSelected()) {
                        navController.navigate(Destination.HOME.route) {
                            popUpTo(Routes.AUTHORIZATION) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(Routes.SELECT_EXAM)
                    }
                },
                onRegisterClick = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.popBackStack() // Возврат на AuthorizationScreen
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(Routes.SELECT_EXAM) {
            SelectExamScreen(
                onOgeClick = {
                    navController.navigate(Destination.HOME.route) {
                        popUpTo(Routes.AUTHORIZATION) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onEgeClick = {
                    navController.navigate(Destination.HOME.route) {
                        popUpTo(Routes.AUTHORIZATION) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        // повторный выбор экзамена из Home: очищаем стек до HOME, чтобы не вернуться на старый Home/Select
        composable(Routes.SELECT_EXAM_FROM_HOME) {
            SelectExamScreen(
                onOgeClick = {
                    navController.navigate(Destination.HOME.route) {
                        popUpTo(Destination.HOME.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onEgeClick = {
                    navController.navigate(Destination.HOME.route) {
                        popUpTo(Destination.HOME.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.PROFILE -> ProfileScreen() {
                        navController.navigate(it)
                    }

                    Destination.FAVORITES -> FavoriteScreen()
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
                onSectionClick = { subjectId, year, trainingSection ->
                    when (trainingSection) {
                        "tickets" -> navController.navigate("tickets/$subjectId/$year/$trainingSection")
                        "all-questions" -> navController.navigate("task-selection/$subjectId/$year/$trainingSection")
                        "theory" -> navController.navigate("theory/$subjectId/$year")
                    }
                },
                onNavigate = { navController.popBackStack() }
            )
        }

        // переход на экран выбора типа заданий
        composable("task-selection/{subjectId}/{year}/{training-section}") { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
            val year = backStackEntry.arguments?.getString("year")?.toIntOrNull() ?: 0
            val trainingSection = backStackEntry.arguments?.getString("training-section") ?: ""
            TaskSelectionTicketScreen(
                subjectId = subjectId,
                year = year,
                trainingSection = trainingSection,
                onTaskClick = { subjectId, year, trainingSection, taskId ->
                    navController.navigate("all-questions/$subjectId/$year/$trainingSection/$taskId")
                },
                onNavigate = { navController.popBackStack() }
            )
        }

        // переход на экран выбора билета
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

        // переход на экран всех вопросов (ЕДИНСТВЕННЫЙ МАРШРУТ)
        composable("all-questions/{subjectId}/{year}/{training-section}/{taskId}") { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
            val year = backStackEntry.arguments?.getString("year")?.toIntOrNull() ?: 0
            val trainingSection = backStackEntry.arguments?.getString("training-section") ?: ""
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            AllQuestionsScreen(
                subjectId = subjectId,
                year = year,
                trainingSection = trainingSection,
                taskId = taskId,
                onBackClick = { navController.popBackStack() }
            )
        }

        // переход на экран теории
        composable("theory/{subjectId}/{year}") { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
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
    // Отслеживаем текущий маршрут
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = Destination.entries.any { it.route == currentRoute }
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar) {
                // нижняя часть кода отвечает за анимацию появления и исчезновения нижней панели
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = slideInVertically(
                        animationSpec = tween(500), // Уменьшили время анимации для более быстрого отклика
                        initialOffsetY = { fullHeight -> fullHeight },
                    ),
                    exit = slideOutVertically(
                        animationSpec = tween(500),
                        targetOffsetY = { fullHeight -> fullHeight },
                    )
                ) {
                    NavigationBar(
                        containerColor = White,
                        windowInsets = NavigationBarDefaults.windowInsets
                    ) {
                        Destination.entries.forEachIndexed { index, destination ->
                            val selected = currentRoute == destination.route
                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    navController.navigate(route = destination.route) {
                                        popUpTo(Destination.HOME.route) {
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
                                        tint = if (selected) Color(0xFFFFFFFF) else Color(
                                            0xFF000000
                                        )
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(id = destination.label),
                                        color = if (selected) Color(0xFF000000) else Color(
                                            0xFF000000
                                        ) // или другой контрастный цвет,
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color(0xCC2a5679) // фон выделенного таба
                                )
                            )
                        }
                    }
                }
            }
        }
    ) { contentPadding ->
        AppNavHost(navController, modifier = Modifier.padding(contentPadding))
    }
}