package com.example.offlineengine.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.offlineengine.presentation.features.onboarding.navgraph.onboardingGraph

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        onboardingGraph()
    }
}