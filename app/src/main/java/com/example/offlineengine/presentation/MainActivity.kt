package com.example.offlineengine.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import com.example.offlineengine.presentation.navigation.AppNavGraph
import com.example.offlineengine.presentation.theme.OfflineEngineTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Just enable edge-to-edge with no color args.
        // Color is handled per-screen by ScreenWithStatusBar wrapper.
        enableEdgeToEdge()

        setContent {
            OfflineEngineTheme {
                AppNavGraph()
            }
        }
    }
}

// Controls ONLY the icon appearance (light or dark icons).
// Works on all API levels because it touches the controller, not the color.
// Color is handled by drawing behind the status bar — the only reliable
// approach on API 35+.
@Composable
fun StatusBarIconEffect(darkIcons: Boolean) {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as ComponentActivity).window
        WindowInsetsControllerCompat(window, view)
            .isAppearanceLightStatusBars = darkIcons
    }
}