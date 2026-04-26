package com.example.offlineengine.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.offlineengine.data.local.datastore.proto.UserSettings
import com.example.offlineengine.data.local.datastore.proto.UserSettingsDataStoreImpl
import com.example.offlineengine.data.local.datastore.proto.UserSettingsSerializer
import com.example.offlineengine.domain.repository.UserSettingsRepository
import com.example.offlineengine.domain.usecase.UserSettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private val Context.userSettingsDataStore : DataStore<UserSettings> by dataStore(
    fileName = "user_settings.pb",
    serializer = UserSettingsSerializer()
)


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<UserSettings> {
        return context.userSettingsDataStore
    }


    @Provides
    @Singleton
    fun provideUserSettingsDataStore(
        dataStore: DataStore<UserSettings>
    ): UserSettingsRepository {
        return UserSettingsDataStoreImpl(dataStore)
    }

    @Provides
    @Singleton
    fun providesUserSettingsUseCase(userSettingsRepository: UserSettingsRepository): UserSettingsUseCase {
        return UserSettingsUseCase(userSettingsRepository)
    }
}