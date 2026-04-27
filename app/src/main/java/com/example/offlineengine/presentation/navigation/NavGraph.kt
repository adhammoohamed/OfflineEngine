package com.example.offlineengine.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.offlineengine.presentation.AppViewModel
import com.example.offlineengine.presentation.features.home.navigation.homeNavGraph
import com.example.offlineengine.presentation.features.onboarding.navgraph.onboardingGraph
import com.example.offlineengine.presentation.features.select_country.navigation.selectYourCountry
import com.example.offlineengine.presentation.features.router.navigation.routerNavGraph
import com.example.offlineengine.presentation.features.select_category.navigation.selectCategory

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    appViewModel: AppViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Router.route  // ALWAYS static
    ) {
        routerNavGraph(navController, appViewModel)
        onboardingGraph(navController)
        selectYourCountry(navController)
        selectCategory(navController)
        homeNavGraph(navController)
    }
}