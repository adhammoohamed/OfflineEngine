package com.example.offlineengine.presentation.features.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.offlineengine.presentation.componenets.ScreenWithStatusBar

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    ScreenWithStatusBar(
        statusBarColor = Color.White,
        darkIcons = true
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "Home Screen")
        }
    }
}