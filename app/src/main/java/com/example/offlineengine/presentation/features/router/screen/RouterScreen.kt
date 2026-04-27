package com.example.offlineengine.presentation.features.router.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.offlineengine.presentation.AppViewModel
import com.example.offlineengine.presentation.StartDestination
import com.example.offlineengine.presentation.StartupState
import com.example.offlineengine.presentation.navigation.Screen

// RouterScreen.kt
@Composable
fun RouterScreen(
    appViewModel: AppViewModel,
    navController: NavHostController
) {
    val startupState by appViewModel.startupState.collectAsStateWithLifecycle()

    // By the time this composable renders, state is ALREADY Ready
    // (because MainActivity's splash kept the screen until then)
    // This LaunchedEffect fires exactly once when state becomes Ready
    LaunchedEffect(startupState) {
        if (startupState is StartupState.Ready) {
            val destination = (startupState as StartupState.Ready).destination
            val route = when (destination) {
                StartDestination.Onboarding -> Screen.Onboarding.route
                StartDestination.SelectCountry -> Screen.SelectCountry.route
                StartDestination.SelectCategory -> Screen.SelectCategory.route
                StartDestination.Home -> Screen.Home.route
            }

            Log.d("RouterScreen", "RouterScreen: $route")
            navController.navigate(route) {
                popUpTo(Screen.Router.route) { inclusive = true }
            }
        }
    }

    // Show nothing — or your brand color as a Box
    // In practice this renders for ~0 frames because splash covers it
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )
}