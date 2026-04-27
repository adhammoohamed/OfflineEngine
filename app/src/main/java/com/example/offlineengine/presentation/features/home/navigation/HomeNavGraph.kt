package com.example.offlineengine.presentation.features.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.offlineengine.presentation.features.home.screen.HomeScreen
import com.example.offlineengine.presentation.navigation.Screen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    composable(route = Screen.Home.route) {
        HomeScreen()
    }
}