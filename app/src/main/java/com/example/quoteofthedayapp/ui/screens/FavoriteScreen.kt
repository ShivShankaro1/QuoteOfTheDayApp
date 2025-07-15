package com.example.quoteofthedayapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quoteofthedayapp.viewmodel.QuoteViewModel
import com.example.quoteofthedayapp.model.QuoteResponse

@Composable
fun FavoriteScreen(
    viewModel: QuoteViewModel,
    onBack: () -> Unit
) {
    val favorites by viewModel.favoriteQuotes.collectAsState()

    var isSelecting by remember { mutableStateOf(false) }
    val selectedQuotes = remember { mutableStateListOf<QuoteResponse>() }
    var selectAll by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top bar with title + buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Favorite Quotes",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )

            if (isSelecting) {
                Button(
                    onClick = {
                        selectedQuotes.forEach { viewModel.removeFavorite(it) }
                        selectedQuotes.clear()
                        isSelecting = false
                        selectAll = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Delete Selected", color = Color.White)
                }
            } else {
                Button(
                    onClick = { isSelecting = true },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Remove")
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = onBack) {
                Text("Back")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No favorites yet!",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            if (isSelecting) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selectAll,
                        onCheckedChange = { checked ->
                            selectAll = checked
                            selectedQuotes.clear()
                            if (checked) selectedQuotes.addAll(favorites)
                        }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Select All")
                }
            }

            LazyColumn {
                items(favorites) { quote ->
                    val isChecked = selectedQuotes.contains(quote)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            if (isSelecting) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = isChecked,
                                        onCheckedChange = { checked ->
                                            if (checked) {
                                                selectedQuotes.add(quote)
                                                if (selectedQuotes.size == favorites.size) {
                                                    selectAll = true
                                                }
                                            } else {
                                                selectedQuotes.remove(quote)
                                                selectAll = false
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Select to remove")
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            Text(
                                text = "“${quote.q}”",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "- ${quote.a}",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
