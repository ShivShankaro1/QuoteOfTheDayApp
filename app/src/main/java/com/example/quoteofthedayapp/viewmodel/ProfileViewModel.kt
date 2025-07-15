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

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _bio = MutableStateFlow("")
    val bio: StateFlow<String> = _bio

    private val _imageUri = MutableStateFlow("")
    val imageUri: StateFlow<String> = _imageUri

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _mobile = MutableStateFlow("")
    val mobile: StateFlow<String> = _mobile

    private val _dob = MutableStateFlow("")
    val dob: StateFlow<String> = _dob

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address

    init {
        viewModelScope.launch {
            launch { prefs.profileName.collectLatest { _name.value = it } }
            launch { prefs.profileBio.collectLatest { _bio.value = it } }
            launch { prefs.profileImageUri.collectLatest { _imageUri.value = it } }
            launch { prefs.profileEmail.collectLatest { _email.value = it } }
            launch { prefs.profileMobile.collectLatest { _mobile.value = it } }
            launch { prefs.profileDOB.collectLatest { _dob.value = it } }
            launch { prefs.profileGender.collectLatest { _gender.value = it } }
            launch { prefs.profileAddress.collectLatest { _address.value = it } }
        }
    }

    fun updateProfile(
        name: String,
        bio: String,
        imageUri: String,
        email: String,
        mobile: String,
        dob: String,
        gender: String,
        address: String
    ) {
        viewModelScope.launch {
            prefs.saveProfile(name, bio, imageUri, email, mobile, dob, gender, address)
        }
    }
}
