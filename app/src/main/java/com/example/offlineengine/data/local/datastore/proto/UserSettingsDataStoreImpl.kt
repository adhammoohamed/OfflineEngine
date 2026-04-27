package com.example.offlineengine.data.local.datastore.proto

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.offlineengine.domain.repository.ReadingPreferencesDomain
import com.example.offlineengine.domain.repository.UserSettingsDomain
import com.example.offlineengine.domain.repository.UserSettingsRepository
import com.example.offlineengine.domain.repository.toDomain
import com.example.offlineengine.domain.repository.toProto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSettingsDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<UserSettings>
) : UserSettingsRepository {
    override fun getUserSettings(): Flow<UserSettingsDomain> {
        return dataStore.data.map { proto -> proto.toDomain() }
    }

    override suspend fun setUserSettings(userSettings: UserSettingsDomain) {
        dataStore.updateData { proto ->
            userSettings.toProto()
        }
    }

    override suspend fun updateCategory(category: String) {
        dataStore.updateData { proto ->
            proto.toBuilder().setCategory(category).build()
        }
    }

    override suspend fun updateCountry(country: String) {
        dataStore.updateData { proto ->
            proto.toBuilder().setCountry(country).build()
        }
    }

    override suspend fun toggleDarkMode() {
        dataStore.updateData { proto ->
            proto.toBuilder().setDarkTheme(!proto.darkTheme).build()
        }
    }

    override suspend fun toggleNotifications() {
        dataStore.updateData { proto ->
            proto.toBuilder().setNotificationsEnabled(!proto.notificationsEnabled).build()
        }
    }

    override suspend fun toggleAutomaticDownloads() {
        dataStore.updateData { proto ->
            proto.toBuilder().setAutomaticDownloads(!proto.automaticDownloads).build()
        }
    }

    override suspend fun updateReadingPreferences(readingPreferences: ReadingPreferencesDomain) {
        dataStore.updateData { proto ->
            proto.toBuilder().setReadingPreferences(readingPreferences.toProto()).build()
        }
    }

    override suspend fun updateLanguage(language: String) {
        dataStore.updateData { proto ->
            proto.toBuilder().setLanguage(language).build()
        }
    }

    override suspend fun updateDefaultCategory(defaultCategory: String) {
        dataStore.updateData { proto ->
            val updatedReadingPreferences = proto.readingPreferences.toBuilder()
                .setDefaultCategory(defaultCategory)
                .build()

            proto.toBuilder().setReadingPreferences(updatedReadingPreferences).build()

        }
    }

    override suspend fun updateCompactMode(compactMode: Boolean) {
        dataStore.updateData { proto ->
            val updatedReadingPreferences = proto.readingPreferences.toBuilder()
                .setCompactMode(compactMode)
                .build()

            proto.toBuilder().setReadingPreferences(updatedReadingPreferences).build()

        }
    }

    override suspend fun updateShowImages(showImages: Boolean) {
        dataStore.updateData { proto ->
            val updatedReadingPreferences = proto.readingPreferences.toBuilder()
                .setShowImages(showImages)
                .build()

            proto.toBuilder().setReadingPreferences(updatedReadingPreferences).build()

        }
    }

    override suspend fun toggleOnboardingState() {
        dataStore.updateData { proto ->
            Log.d("userSettingsUseCase", "toggleOnboardingState: ${proto.onboarded}")
            proto.toBuilder().setOnboarded(!proto.onboarded).build()
        }
    }
}