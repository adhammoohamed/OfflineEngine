package com.example.offlineengine.presentation.features.onboarding.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.offlineengine.presentation.features.onboarding.screens.OnboardingScreen
import com.example.offlineengine.presentation.navigation.Screen

fun NavGraphBuilder.onboardingGraph(
//    onboardingViewModel: OnboardingViewModel = hiltViewModel()
) {
    composable(route = Screen.Onboarding.route) {
        OnboardingScreen()
    }
}