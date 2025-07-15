package com.example.quoteofthedayapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.quoteofthedayapp.ui.navigation.Screen
import com.example.quoteofthedayapp.viewmodel.ProfileViewModel
import com.example.quoteofthedayapp.viewmodel.QuoteViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: QuoteViewModel,
    viewModel2: ProfileViewModel
) {
    val userName by viewModel2.name.collectAsStateWithLifecycle()
    val quote by viewModel.quote.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hi, ${if (userName.isNotBlank()) userName else "there"}!",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (isLoading) {
            ShimmerQuoteCard()
        } else {
            QuoteCard(quote.q, quote.a)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (!isLoading) {
                    isLoading = true
                    viewModel.getNextQuote()
                    coroutineScope.launch {
                        delay(1500)
                        isLoading = false
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next Quote")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { viewModel.saveFavorite() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add to Favorites")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "“${quote.q}” — ${quote.a}")
                }
                val chooser = Intent.createChooser(shareIntent, "Share quote via...")
                context.startActivity(chooser)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Share Quote")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate(Screen.Search.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Search")
        }
    }
}

@Composable
fun QuoteCard(q: String, a: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E88E5))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "“$q”",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "- $a",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ShimmerQuoteCard() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.6f)
    )
    val shimmerBrush = Brush.linearGradient(shimmerColors)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(shimmerBrush)
        )
    }
}
