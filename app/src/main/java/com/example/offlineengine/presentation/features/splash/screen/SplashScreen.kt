package com.example.offlineengine.presentation.features.splash.screen

import android.util.Log
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.offlineengine.presentation.AppViewModel
import com.example.offlineengine.presentation.navigation.Screen
import com.example.offlineengine.R

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: AppViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        val route = viewModel.getStartDestination()

        navController.navigate(route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(painterResource(R.drawable.logo), null)
    }
}