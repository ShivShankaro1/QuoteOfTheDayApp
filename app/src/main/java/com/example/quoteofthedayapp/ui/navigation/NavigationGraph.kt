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
    viewModel: QuoteViewModel,
    viewModel2: ProfileViewModel,
    viewModel3: ThemeViewModel,
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
                viewModel = viewModel,
                viewModel2 = viewModel2
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(viewModel = viewModel)
        }

        composable(Screen.Favorites.route) {
            FavoriteScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                viewModel = viewModel2,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.More.route) {
            MoreScreen(viewModel = viewModel3)
        }
    }
}
