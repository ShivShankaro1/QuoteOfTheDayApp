package com.example.quoteofthedayapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val icon: ImageVector,
    val title: String // ✅ Add this
) {
    object Home : Screen("home", Icons.Default.Home, "Home")
    object Favorites : Screen("favorites", Icons.Default.Favorite, "Favorites")
    object Profile : Screen("profile", Icons.Default.Person, "Profile")
    object More : Screen("more", Icons.Default.MoreVert, "More")
}
