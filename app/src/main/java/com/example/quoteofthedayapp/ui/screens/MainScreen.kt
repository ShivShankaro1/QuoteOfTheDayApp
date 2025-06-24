package com.example.quoteofthedayapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quoteofthedayapp.ui.components.MainBottomNavigationBar
import com.example.quoteofthedayapp.ui.navigation.NavigationGraph
import com.example.quoteofthedayapp.ui.navigation.Screen
import com.example.quoteofthedayapp.viewmodel.ProfileViewModel
import com.example.quoteofthedayapp.viewmodel.QuoteViewModel
import com.example.quoteofthedayapp.viewmodel.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    quoteViewModel: QuoteViewModel,
    profileViewModel: ProfileViewModel,
    themeViewModel: ThemeViewModel
) {
    val userName by profileViewModel.name.collectAsStateWithLifecycle()
    val initial = userName.firstOrNull()?.uppercase() ?: "?"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty title */ },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Profile.route)
                    }) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = initial,
                                color = Color.White,
                                style = MaterialTheme.typography.labelLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            MainBottomNavigationBar(navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            NavigationGraph(
                navController = navController,
                quoteViewModel = quoteViewModel,
                profileViewModel = profileViewModel,
                themeViewModel = themeViewModel
            )
        }
    }
}
