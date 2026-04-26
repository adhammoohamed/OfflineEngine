package com.example.offlineengine.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.offlineengine.presentation.AppViewModel
import com.example.offlineengine.presentation.features.onboarding.navgraph.onboardingGraph
import com.example.offlineengine.presentation.features.select_country.navigation.selectYourCountry
import com.example.offlineengine.presentation.features.splash.navigation.splashNavGraph

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    appViewModel: AppViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        splashNavGraph(navController)
        onboardingGraph(navController)
        selectYourCountry()
//        homeGraph(navController)
    }
}