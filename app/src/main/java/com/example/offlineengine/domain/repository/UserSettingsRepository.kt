package com.example.offlineengine.domain.repository

import com.example.offlineengine.data.local.datastore.proto.ReadingPreferences
import com.example.offlineengine.data.local.datastore.proto.UserSettings
import kotlinx.coroutines.flow.Flow

data class UserSettingsDomain(
    val country: String,
    val language: String,
    val category: String,
    val darkTheme: Boolean,
    val notificationsEnabled: Boolean,
    val automaticDownloads: Boolean,
    val onboarded: Boolean,
    val readingPreferences: ReadingPreferencesDomain
)

data class ReadingPreferencesDomain(
    val showImages: Boolean,
    val compactMode: Boolean,
    val defaultCategory: String
)

fun ReadingPreferences.toDomain(): ReadingPreferencesDomain {
    return ReadingPreferencesDomain(
        showImages = showImages,
        compactMode = compactMode,
        defaultCategory = defaultCategory
    )
}

fun ReadingPreferencesDomain.toProto(): ReadingPreferences {
    return ReadingPreferences.newBuilder().setCompactMode(compactMode)
        .setDefaultCategory(defaultCategory).setShowImages(showImages).build()
}

fun UserSettingsDomain.toProto(): UserSettings {
    return UserSettings.newBuilder()
        .setCountry(country)
        .setLanguage(language)
        .setCategory(category)
        .setDarkTheme(darkTheme)
        .setNotificationsEnabled(notificationsEnabled)
        .setAutomaticDownloads(automaticDownloads)
        .setReadingPreferences(readingPreferences.toProto())
        .setOnboarded(onboarded)
        .build()
}

fun UserSettings.toDomain(): UserSettingsDomain {
    return UserSettingsDomain(
        country = country,
        language = language,
        category = category,
        darkTheme = darkTheme,
        notificationsEnabled = notificationsEnabled,
        automaticDownloads = automaticDownloads,
        onboarded = onboarded,
        readingPreferences = readingPreferences.toDomain()
    )
}

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
