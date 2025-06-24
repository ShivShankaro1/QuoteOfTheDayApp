package com.example.quoteofthedayapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quoteofthedayapp.ui.screens.*
import com.example.quoteofthedayapp.viewmodel.ProfileViewModel
import com.example.quoteofthedayapp.viewmodel.QuoteViewModel
import com.example.quoteofthedayapp.viewmodel.ThemeViewModel

@Composable
fun AppNavigation(
    profileViewModel: ProfileViewModel,
    quoteViewModel: QuoteViewModel,
    themeViewModel: ThemeViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                quoteViewModel = quoteViewModel,
                profileViewModel = profileViewModel
            )
        }

        composable(Screen.Favorites.route) {
            FavoriteScreen(
                viewModel = quoteViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(profileViewModel = profileViewModel)
        }

        composable(Screen.More.route) {
            MoreScreen(viewModel = themeViewModel)
        }
    }
}
