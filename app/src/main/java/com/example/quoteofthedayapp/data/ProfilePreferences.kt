package com.example.quoteofthedayapp.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "profile_prefs"

private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

class ProfilePreferences(private val context: Context) {

    companion object {
        val NAME_KEY = stringPreferencesKey("name")
        val BIO_KEY = stringPreferencesKey("bio")
        val IMAGE_URI_KEY = stringPreferencesKey("image_uri")
    }

    // Save data
    suspend fun saveProfile(name: String, bio: String, imageUri: String) {
        context.dataStore.edit { prefs ->
            prefs[NAME_KEY] = name
            prefs[BIO_KEY] = bio
            prefs[IMAGE_URI_KEY] = imageUri
        }
    }

    // Read data
    val profileName: Flow<String> = context.dataStore.data
        .map { it[NAME_KEY] ?: "" }

    val profileBio: Flow<String> = context.dataStore.data
        .map { it[BIO_KEY] ?: "" }

    val profileImageUri: Flow<String> = context.dataStore.data
        .map { it[IMAGE_URI_KEY] ?: "" }
}
