package com.example.offlineengine.presentation.features.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.offlineengine.presentation.features.splash.screen.SplashScreen
import com.example.offlineengine.presentation.navigation.Screen

fun NavGraphBuilder.splashNavGraph(navController: NavHostController) {
    composable(route = Screen.Splash.route) {
        SplashScreen(
          navController
        )
    }
}