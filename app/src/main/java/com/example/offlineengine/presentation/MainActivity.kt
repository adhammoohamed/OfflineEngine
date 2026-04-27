package com.example.offlineengine.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsControllerCompat
import com.example.offlineengine.presentation.navigation.AppNavGraph
import com.example.offlineengine.presentation.theme.OfflineEngineTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // MUST be before super.onCreate()
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            viewModel.startupState.value is StartupState.Loading
        }

        enableEdgeToEdge()

        setContent {
            OfflineEngineTheme {
                AppNavGraph()
            }
        }
    }
}

@Composable
fun StatusBarIconEffect(darkIcons: Boolean) {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as ComponentActivity).window
        WindowInsetsControllerCompat(window, view)
            .isAppearanceLightStatusBars = darkIcons
    }
}