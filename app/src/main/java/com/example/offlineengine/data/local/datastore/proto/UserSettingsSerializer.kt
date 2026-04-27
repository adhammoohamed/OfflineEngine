package com.example.offlineengine.data.local.datastore.proto


import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserSettingsSerializer @Inject constructor() : Serializer<UserSettings> {

    // Default value when no data exists yet (first app launch)
    override val defaultValue: UserSettings = UserSettings.newBuilder()
        .setCountry("")
        .setLanguage("")
        .setCategory("")
        .setDarkTheme(false)
        .setNotificationsEnabled(true)
        .setAutomaticDownloads(true)
        .build()

    override suspend fun readFrom(input: InputStream): UserSettings {
        try {
            return UserSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            // File is corrupted — throw CorruptionException so DataStore
            // can handle it (e.g. delete and recreate with defaultValue)
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserSettings, output: OutputStream) {
        t.writeTo(output)
    }
}