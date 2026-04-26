package com.example.offlineengine.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.offlineengine.domain.usecase.UserSettingsUseCase
import com.example.offlineengine.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val userSettingsUseCase: UserSettingsUseCase
) : ViewModel() {

    private val _startDestination: MutableStateFlow<String?> = MutableStateFlow(null)
    val startDestination: StateFlow<String?> = _startDestination.asStateFlow()


    suspend fun getStartDestination(): String {
        val settings = userSettingsUseCase.getUserSettings().first()

        return when {
            !settings.onboarded -> Screen.Onboarding.route
            settings.country.isEmpty() -> Screen.SelectCountry.route
            else -> "Screen.Home.route"
        }
    }
}