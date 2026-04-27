package com.example.offlineengine.data.mapper

import com.example.offlineengine.data.local.datastore.proto.ReadingPreferences
import com.example.offlineengine.data.local.datastore.proto.UserSettings
import com.example.offlineengine.domain.model.ReadingPreferencesDomain
import com.example.offlineengine.domain.model.UserSettingsDomain

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