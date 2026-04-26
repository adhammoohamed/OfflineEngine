package com.example.offlineengine.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen(route = "splash")
    data object Onboarding : Screen(route = "onboarding")
    data object SelectCountry : Screen(route = "select_country")
}