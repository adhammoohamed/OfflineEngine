package com.example.offlineengine.presentation.navigation

sealed class Screen(val route: String) {
    data object Router : Screen("router")
    data object Onboarding : Screen(route = "onboarding")
    data object SelectCountry : Screen(route = "select_country")
    data object Home : Screen(route = "home")
}