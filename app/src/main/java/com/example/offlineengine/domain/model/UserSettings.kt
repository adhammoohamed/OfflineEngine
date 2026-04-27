package com.example.offlineengine.domain.model

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