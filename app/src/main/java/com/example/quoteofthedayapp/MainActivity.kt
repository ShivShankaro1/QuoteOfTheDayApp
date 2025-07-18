package com.example.quoteofthedayapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.quoteofthedayapp.data.QuoteDatabase
import com.example.quoteofthedayapp.data.QuoteRepository
import com.example.quoteofthedayapp.ui.screens.MainScreen
import com.example.quoteofthedayapp.ui.theme.QuoteOfTheDayAppTheme
import com.example.quoteofthedayapp.viewmodel.ProfileViewModel
import com.example.quoteofthedayapp.viewmodel.QuoteViewModel
import com.example.quoteofthedayapp.viewmodel.QuoteViewModelFactory
import com.example.quoteofthedayapp.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {

    private lateinit var quoteViewModel: QuoteViewModel
    private val profileViewModel: ProfileViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = QuoteDatabase.getDatabase(applicationContext)
        val repository = QuoteRepository(database.quoteDao())

        quoteViewModel = ViewModelProvider(
            this,
            QuoteViewModelFactory(repository)
        )[QuoteViewModel::class.java]

        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            Crossfade(targetState = isDarkTheme, label = "ThemeCrossfade") { isDark ->
                QuoteOfTheDayAppTheme(darkTheme = isDark) {
                    // 🔁 This uses your own MainScreen.kt
                    val navController = rememberNavController()

                    MainScreen(
                        navController = navController,
                        viewModel = quoteViewModel,
                        viewModel2 = profileViewModel,
                        viewModel3 = themeViewModel
                    )
                }
            }
        }
    }
}
