@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.quoteofthedayapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.quoteofthedayapp.viewmodel.ProfileViewModel
import com.example.quoteofthedayapp.viewmodel.QuoteViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    quoteViewModel: QuoteViewModel,
    profileViewModel: ProfileViewModel
) {
    val quote = quoteViewModel.currentQuote.value
    val userName by profileViewModel.name.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hi, ${if (userName.isNotBlank()) userName else "there"}!",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Start
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E88E5)),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "“${quote.text}”",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "- ${quote.author}",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { quoteViewModel.nextQuote() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Next Quote")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { quoteViewModel.saveFavorite() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to Favorites")
            }
        }
    }
}
