package com.example.quoteofthedayapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteofthedayapp.data.ProfilePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = ProfilePreferences(application)

    // Mutable state flows for UI binding
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _bio = MutableStateFlow("")
    val bio: StateFlow<String> = _bio

    private val _imageUri = MutableStateFlow("")
    val imageUri: StateFlow<String> = _imageUri

    init {
        // Load saved values from DataStore when ViewModel starts
        viewModelScope.launch {
            launch { prefs.profileName.collectLatest { _name.value = it } }
            launch { prefs.profileBio.collectLatest { _bio.value = it } }
            launch { prefs.profileImageUri.collectLatest { _imageUri.value = it } }
        }
    }

    // Function to save profile updates
    fun updateProfile(name: String, bio: String, imageUri: String) {
        viewModelScope.launch {
            prefs.saveProfile(name, bio, imageUri)
        }
    }
}
