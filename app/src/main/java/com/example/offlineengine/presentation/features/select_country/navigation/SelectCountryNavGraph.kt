package com.example.offlineengine.presentation.features.select_country.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.offlineengine.presentation.features.select_country.screen.SelectYourCountryScreen
import com.example.offlineengine.presentation.navigation.Screen

fun NavGraphBuilder.selectYourCountry() {
    composable(route = Screen.SelectCountry.route) {
        SelectYourCountryScreen()
    }
}