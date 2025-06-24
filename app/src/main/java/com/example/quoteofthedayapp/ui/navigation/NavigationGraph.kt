package com.example.quoteofthedayapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quoteofthedayapp.ui.screens.*
import com.example.quoteofthedayapp.viewmodel.ProfileViewModel
import com.example.quoteofthedayapp.viewmodel.QuoteViewModel
import com.example.quoteofthedayapp.viewmodel.ThemeViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    quoteViewModel: QuoteViewModel,
    profileViewModel: ProfileViewModel,
    themeViewModel: ThemeViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                quoteViewModel = quoteViewModel,
                profileViewModel = profileViewModel
            )
        }
        composable(Screen.Favorites.route) {
            FavoriteScreen(viewModel = quoteViewModel, onBack = {})
        }
        composable(Screen.Profile.route) {
            ProfileScreen(profileViewModel = profileViewModel) // ✅ FIXED
        }
        composable(Screen.More.route) {
            MoreScreen(viewModel = themeViewModel)
        }
    }
}
