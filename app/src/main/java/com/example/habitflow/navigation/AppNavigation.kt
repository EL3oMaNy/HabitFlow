package com.example.habitflow.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habitflow.ui.screens.AddHabitScreen
import com.example.habitflow.ui.screens.DashboardScreen
import com.example.habitflow.ui.screens.HabitModel
import com.example.habitflow.ui.screens.SettingsScreen
import com.example.habitflow.ui.screens.SplashScreen

@Composable
fun AppNavigation(
    isDarkTheme: Boolean,
    isArabic: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onLanguageChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    val habitsList = remember {
        mutableStateListOf(
            HabitModel("القراءة", "Reading", "30 دقيقة يومياً", "30 mins daily", "معرفي", "Knowledge", Icons.Outlined.Book, 1f, true),
            HabitModel("شرب الماء", "Water Intake", "8 أكواب", "8 Glasses", "صحة", "Health", Icons.Outlined.WaterDrop, 0.62f, false),
            HabitModel("التمارين الرياضية", "Exercise", "45 دقيقة", "45 mins", "بدني", "Fitness", Icons.Outlined.FitnessCenter, 1f, true)
        )
    }

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(
                isArabic = isArabic,
                onNavigateToDashboard = {
                    navController.navigate("dashboard") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("dashboard") {
            DashboardScreen(
                isArabic = isArabic,
                habitsList = habitsList,
                onNavigateToAddHabit = { navController.navigate("add_habit") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }

        composable("add_habit") {
            AddHabitScreen(
                isArabic = isArabic,
                onHabitCreated = { newHabit ->
                    habitsList.add(newHabit)
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("settings") {
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                isArabic = isArabic,
                onThemeChange = onThemeChange,
                onLanguageChange = onLanguageChange,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}