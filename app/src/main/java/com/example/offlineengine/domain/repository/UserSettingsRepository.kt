package com.example.offlineengine.domain.repository

import com.example.offlineengine.domain.model.ReadingPreferencesDomain
import com.example.offlineengine.domain.model.UserSettingsDomain
import kotlinx.coroutines.flow.Flow


interface UserSettingsRepository {
    fun getUserSettings(): Flow<UserSettingsDomain>
    suspend fun setUserSettings(userSettings: UserSettingsDomain)
    suspend fun updateCategory(category: String)
    suspend fun updateCountry(country: String)
    suspend fun toggleDarkMode()
    suspend fun toggleNotifications()
    suspend fun toggleAutomaticDownloads()
    suspend fun updateReadingPreferences(readingPreferences: ReadingPreferencesDomain)
    suspend fun updateLanguage(language: String)
    suspend fun updateDefaultCategory(defaultCategory: String)
    suspend fun updateCompactMode(compactMode: Boolean)
    suspend fun updateShowImages(showImages: Boolean)
    suspend fun toggleOnboardingState()

}
