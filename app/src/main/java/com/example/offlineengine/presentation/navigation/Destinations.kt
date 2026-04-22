package com.example.offlineengine.presentation.navigation

sealed class Screen(val route: String) {
    data object Onboarding : Screen(route = "onboarding")
}