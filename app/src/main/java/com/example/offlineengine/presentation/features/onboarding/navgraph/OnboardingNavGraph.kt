package com.example.offlineengine.presentation.features.onboarding.navgraph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.offlineengine.presentation.features.onboarding.screens.OnboardingScreen
import com.example.offlineengine.presentation.features.onboarding.viewmodel.OnboardingViewModel
import com.example.offlineengine.presentation.navigation.Screen

fun NavGraphBuilder.onboardingGraph(
    navController: NavHostController,

    ) {
    composable(route = Screen.Onboarding.route) {
        val viewModel: OnboardingViewModel = hiltViewModel()

        OnboardingScreen(
            viewModel = viewModel,
            onNext = { viewModel.next() },
            onBack = { viewModel.back() },
            onFinish = {
                viewModel.finish()
                navController.navigate(Screen.SelectCountry.route)
            }
        )
    }
}