package com.example.offlineengine.presentation.features.select_country.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.offlineengine.presentation.features.select_country.screen.SelectYourCountryScreen
import com.example.offlineengine.presentation.navigation.Screen

fun NavGraphBuilder.selectYourCountry(navController: NavHostController) {
    composable(route = Screen.SelectCountry.route) {
        SelectYourCountryScreen {
            navController.navigate(Screen.Home.route)
        }
    }
}