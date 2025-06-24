package com.example.quoteofthedayapp

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quoteofthedayapp.viewmodel.QuoteViewModel


@Composable
fun QuoteScreen(viewModel: QuoteViewModel, onShowFavorites: () -> Unit) {
    val quote = viewModel.currentQuote.value
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Quote of the Day",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (quote.text.isNotBlank()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE1F5FE)),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "“${quote.text}”",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "- ${quote.author}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                        color = Color.DarkGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.nextQuote() },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Next Quote")
            }

            Button(
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "“${quote.text}” - ${quote.author}")
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share Quote"))
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Share")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.saveFavorite()
                Toast.makeText(context, "Saved to Favorites", Toast.LENGTH_SHORT).show()
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Save to Favorites")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onShowFavorites() },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("View Favorites")
        }
    }
}
