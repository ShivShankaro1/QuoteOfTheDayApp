package com.example.quoteofthedayapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

// Extension property for DataStore
private val Context.dataStore by preferencesDataStore(name = "settings")

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val THEME_KEY = booleanPreferencesKey("dark_theme_enabled")

    // Exposed StateFlow for UI observation
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        // Observe DataStore and update internal state
        viewModelScope.launch {
            context.dataStore.data
                .map { it[THEME_KEY] ?: false }
                .collect { isDark ->
                    _isDarkTheme.value = isDark
                }
        }
    }

    fun toggleTheme(enabled: Boolean) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[THEME_KEY] = enabled
            }
        }
    }
}
