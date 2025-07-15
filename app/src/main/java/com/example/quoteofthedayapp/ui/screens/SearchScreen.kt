package com.example.quoteofthedayapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.quoteofthedayapp.viewmodel.QuoteViewModel
import com.example.quoteofthedayapp.model.QuoteResponse

@Composable
fun SearchScreen(viewModel: QuoteViewModel) {
    val allFavorites by viewModel.favoriteQuotes.collectAsState()
    var query by remember { mutableStateOf(TextFieldValue("")) }

    val filtered = allFavorites.filter {
        it.q.contains(query.text, ignoreCase = true) ||
                it.a.contains(query.text, ignoreCase = true)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search Favorites") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (filtered.isEmpty()) {
            Text("No matching favorites found.")
        } else {
            LazyColumn {
                items(filtered) { quote ->
                    QuoteCard(quote)
                }
            }
        }
    }
}

@Composable
fun QuoteCard(quote: QuoteResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("“${quote.q}”", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("- ${quote.a}", style = MaterialTheme.typography.labelMedium)
        }
    }
}
