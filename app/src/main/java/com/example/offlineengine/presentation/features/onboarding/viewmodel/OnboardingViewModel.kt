package com.example.offlineengine.presentation.features.onboarding.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.offlineengine.R
import com.example.offlineengine.domain.repository.UserSettingsRepository
import com.example.offlineengine.domain.usecase.UserSettingsUseCase
import com.example.offlineengine.presentation.features.onboarding.screens.OnboardingPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userSettingsUseCase: UserSettingsUseCase
) : ViewModel(){
    val pages = listOf(
        OnboardingPage(
            image = R.drawable.onboarding_1,
            title = "Lorem Ipsum is simply dummy",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        ), OnboardingPage(
            image = R.drawable.onboarding_2,
            title = "Lorem Ipsum is simply dummy",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        ), OnboardingPage(
            image = R.drawable.onboarding_3,
            title = "Lorem Ipsum is simply dummy",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        )
    )

    var currentPage = mutableStateOf(0)
        private set

    fun next() {
        if (currentPage.value < pages.lastIndex)
            currentPage.value++
    }

    fun back() {
        if (currentPage.value > 0)
            currentPage.value--
    }

    fun finish() {
        viewModelScope.launch {
            toggleOnboardingState()
            Log.d("userSettingsUseCase", "finish: ${userSettingsUseCase.getUserSettings().first().onboarded}")
        }
    }
    suspend fun toggleOnboardingState() {
        userSettingsUseCase.toggleOnboardingState()
    }
}