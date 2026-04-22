package com.example.offlineengine.presentation.componenets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.example.offlineengine.presentation.StatusBarIconEffect

@Composable
fun ScreenWithStatusBar(
    modifier: Modifier = Modifier,
    statusBarColor: Color,
    // darkIcons = true  → dark/black icons  → use on LIGHT backgrounds
    // darkIcons = false → light/white icons  → use on DARK backgrounds
    darkIcons: Boolean = false,
    content: @Composable () -> Unit
) {
    // Control icon color — works on all API levels
    StatusBarIconEffect(darkIcons = darkIcons)
    Box(modifier = modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            content()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(statusBarColor)  // already there ✅
                .zIndex(1f)
        )
    }
}