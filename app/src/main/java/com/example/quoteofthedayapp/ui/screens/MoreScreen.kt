package com.example.quoteofthedayapp.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.quoteofthedayapp.viewmodel.ThemeViewModel

@Composable
fun MoreScreen(viewModel: ThemeViewModel) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val context = LocalContext.current
    var showRatingDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "More",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // ✅ Dark Theme Toggle
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.Brightness6, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Dark Theme", modifier = Modifier.weight(1f))
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { viewModel.toggleTheme(it) }
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // ✅ Rate this app (custom in-app dialog)
        MoreOptionItem(
            icon = Icons.Default.Star,
            title = "Rate this App"
        ) {
            showRatingDialog = true
        }

        // ✅ Share this app
        MoreOptionItem(
            icon = Icons.Default.Share,
            title = "Share App"
        ) {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out this awesome app:\nhttps://play.google.com/store/apps/details?id=${context.packageName}"
                )
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share App"))
        }

        // ✅ Feedback
        MoreOptionItem(
            icon = Icons.Default.Email,
            title = "Send Feedback"
        ) {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("your_email@example.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Quote of the Day App Feedback")
            }
            try {
                context.startActivity(emailIntent)
            } catch (e: Exception) {
                Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show()
            }
        }

        // ✅ About App
        MoreOptionItem(
            icon = Icons.Default.Info,
            title = "About App"
        ) {
            Toast.makeText(
                context,
                "Quote of the Day App v1.0\nDeveloped by NORMAL PLAYER",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Show rating dialog if triggered
    if (showRatingDialog) {
        RatingDialog(
            onSubmit = { rating ->
                Toast.makeText(context, "Thanks for rating: $rating star(s)", Toast.LENGTH_SHORT).show()
                showRatingDialog = false
            },
            onDismiss = { showRatingDialog = false }
        )
    }
}

@Composable
fun MoreOptionItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(title) },
        leadingContent = { Icon(icon, contentDescription = title) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    )
}

@Composable
fun RatingDialog(
    onSubmit: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedRating by remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    if (selectedRating > 0) onSubmit(selectedRating)
                    onDismiss()
                }
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Rate this App") },
        text = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in 1..5) {
                    IconToggleButton(
                        checked = i <= selectedRating,
                        onCheckedChange = { selectedRating = i }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star $i",
                            tint = if (i <= selectedRating) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }
                }
            }
        }
    )
}
