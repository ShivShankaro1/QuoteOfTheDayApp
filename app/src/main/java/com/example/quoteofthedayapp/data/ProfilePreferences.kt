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
        val EMAIL_KEY = stringPreferencesKey("email")
        val MOBILE_KEY = stringPreferencesKey("mobile")
        val DOB_KEY = stringPreferencesKey("dob")
        val GENDER_KEY = stringPreferencesKey("gender")
        val ADDRESS_KEY = stringPreferencesKey("address")
    }

    // ✅ Save all fields
    suspend fun saveProfile(
        name: String,
        bio: String,
        imageUri: String,
        email: String,
        mobile: String,
        dob: String,
        gender: String,
        address: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[NAME_KEY] = name
            prefs[BIO_KEY] = bio
            prefs[IMAGE_URI_KEY] = imageUri
            prefs[EMAIL_KEY] = email
            prefs[MOBILE_KEY] = mobile
            prefs[DOB_KEY] = dob
            prefs[GENDER_KEY] = gender
            prefs[ADDRESS_KEY] = address
        }
    }

    // ✅ Expose each field as a Flow
    val profileName: Flow<String> = context.dataStore.data.map { it[NAME_KEY] ?: "" }
    val profileBio: Flow<String> = context.dataStore.data.map { it[BIO_KEY] ?: "" }
    val profileImageUri: Flow<String> = context.dataStore.data.map { it[IMAGE_URI_KEY] ?: "" }
    val profileEmail: Flow<String> = context.dataStore.data.map { it[EMAIL_KEY] ?: "" }
    val profileMobile: Flow<String> = context.dataStore.data.map { it[MOBILE_KEY] ?: "" }
    val profileDOB: Flow<String> = context.dataStore.data.map { it[DOB_KEY] ?: "" }
    val profileGender: Flow<String> = context.dataStore.data.map { it[GENDER_KEY] ?: "" }
    val profileAddress: Flow<String> = context.dataStore.data.map { it[ADDRESS_KEY] ?: "" }
}
