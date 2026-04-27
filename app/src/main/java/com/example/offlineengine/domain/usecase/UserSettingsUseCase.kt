package com.example.offlineengine.domain.usecase

import com.example.offlineengine.domain.model.ReadingPreferencesDomain
import com.example.offlineengine.domain.model.UserSettingsDomain
import com.example.offlineengine.domain.repository.UserSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSettingsUseCase @Inject constructor(
    private val repository: UserSettingsRepository
) {
    fun getUserSettings() = repository.getUserSettings()
    suspend fun setUserSettings(userSettings: UserSettingsDomain) = repository.setUserSettings(userSettings)
    suspend fun updateCountry(country: String) = repository.updateCountry(country)
    suspend fun updateCategory(category: String) = repository.updateCategory(category)
    suspend fun toggleDarkMode() = repository.toggleDarkMode()
    suspend fun toggleNotifications() = repository.toggleNotifications()
    suspend fun toggleAutomaticDownloads() = repository.toggleAutomaticDownloads()
    suspend fun updateReadingPreferences(readingPreferences: ReadingPreferencesDomain) = repository.updateReadingPreferences(readingPreferences)
    suspend fun updateDefaultCategory(defaultCategory: String) = repository.updateDefaultCategory(defaultCategory)
    suspend fun updateLanguage(language: String) = repository.updateLanguage(language)
    suspend fun updateCompactMode(compactMode: Boolean) = repository.updateCompactMode(compactMode)
    suspend fun updateShowImages(showImages: Boolean) = repository.updateShowImages(showImages)
    suspend fun toggleOnboardingState() = repository.toggleOnboardingState()

}

