package com.example.offlineengine.presentation.features.router.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.offlineengine.presentation.AppViewModel
import com.example.offlineengine.presentation.features.router.screen.RouterScreen
import com.example.offlineengine.presentation.navigation.Screen

fun NavGraphBuilder.routerNavGraph(navController: NavHostController, appViewModel: AppViewModel) {
    composable(route = Screen.Router.route) {
        RouterScreen(
            appViewModel = appViewModel,
            navController = navController
        )
    }
}