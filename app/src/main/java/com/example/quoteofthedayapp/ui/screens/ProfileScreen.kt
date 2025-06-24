package com.example.quoteofthedayapp.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.quoteofthedayapp.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = viewModel()) {
    val name by profileViewModel.name.collectAsState()
    val bio by profileViewModel.bio.collectAsState()
    val imageUri by profileViewModel.imageUri.collectAsState()

    var editableName by remember { mutableStateOf(name) }
    var editableEmail by remember { mutableStateOf("sptb1999@gmail.com") }
    var editableMobile by remember { mutableStateOf("8287354548") }

    var isEditingName by remember { mutableStateOf(false) }
    var isEditingEmail by remember { mutableStateOf(false) }
    var isEditingMobile by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text("Update Profile", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val painter = rememberAsyncImagePainter(model = if (imageUri.isNotBlank()) Uri.parse(imageUri) else null)
            Image(
                painter = painter,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .clickable { /* Optional: image picker */ }
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileField(
                label = "Name",
                value = editableName,
                isEditing = isEditingName,
                onValueChange = { editableName = it },
                onEditClick = { isEditingName = !isEditingName }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileField(
                label = "Mobile Number",
                value = editableMobile,
                isEditing = isEditingMobile,
                onValueChange = { editableMobile = it },
                onEditClick = { isEditingMobile = !isEditingMobile }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileField(
                label = "Email",
                value = editableEmail,
                isEditing = isEditingEmail,
                onValueChange = { editableEmail = it },
                onEditClick = { isEditingEmail = !isEditingEmail }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    profileViewModel.updateProfile(
                        name = editableName,
                        bio = bio,
                        imageUri = imageUri
                    )
                    // Save other details if needed in preferences
                    isEditingName = false
                    isEditingEmail = false
                    isEditingMobile = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Save", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ProfileField(
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit,
    onEditClick: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("$label:", color = Color.White) },
        enabled = isEditing,
        trailingIcon = {
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.White
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.DarkGray,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.White
        )
    )
}
