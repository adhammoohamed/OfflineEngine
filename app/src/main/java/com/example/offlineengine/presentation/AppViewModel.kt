package com.example.offlineengine.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.offlineengine.domain.usecase.UserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class
AppViewModel @Inject constructor(
    private val userSettingsUseCase: UserSettingsUseCase
) : ViewModel() {

    private val _startupState = MutableStateFlow<StartupState>(StartupState.Loading)
    val startupState: StateFlow<StartupState> = _startupState.asStateFlow()

    init {
        viewModelScope.launch {
            // All async work happens HERE, not in a composable
            val destination = resolveStartDestination()
            _startupState.value = StartupState.Ready(destination)
        }
    }

    private suspend fun resolveStartDestination(): StartDestination {
        val user = userSettingsUseCase.getUserSettings().first()   // suspend: reads DataStore
        return when {
            !user.onboarded -> StartDestination.Onboarding
            user.country.isEmpty() -> StartDestination.SelectCountry
            else -> StartDestination.Home
        }
    }
}

// StartupState.kt
sealed interface StartupState {
    data object Loading : StartupState
    data class Ready(val destination: StartDestination) : StartupState
}

enum class StartDestination {
    Onboarding,
    SelectCountry,
    Home
}