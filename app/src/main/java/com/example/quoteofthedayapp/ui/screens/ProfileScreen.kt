package com.example.quoteofthedayapp.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.quoteofthedayapp.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    val name by viewModel.name.collectAsState()
    val bio by viewModel.bio.collectAsState()
    val imageUri by viewModel.imageUri.collectAsState()
    val email by viewModel.email.collectAsState()
    val mobile by viewModel.mobile.collectAsState()
    val dob by viewModel.dob.collectAsState()
    val gender by viewModel.gender.collectAsState()
    val address by viewModel.address.collectAsState()

    var editableName by remember { mutableStateOf(name) }
    var editableEmail by remember { mutableStateOf(email) }
    var editableMobile by remember { mutableStateOf(mobile) }
    var editableDob by remember { mutableStateOf(dob) }
    var editableGender by remember { mutableStateOf(gender) }
    var editableAddress by remember { mutableStateOf(address) }

    var isEditing by remember { mutableStateOf(false) }

    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.updateProfile(
                editableName,
                bio,
                it.toString(),
                editableEmail,
                editableMobile,
                editableDob,
                editableGender,
                editableAddress
            )
        }
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text("Update Profile", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val painter = rememberAsyncImagePainter(model = if (imageUri.isNotBlank()) Uri.parse(imageUri) else null)

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable { imagePicker.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painter,
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ProfileField("Name", editableName, isEditing) { editableName = it }
            Spacer(modifier = Modifier.height(12.dp))
            ProfileField("Mobile Number", editableMobile, isEditing) { editableMobile = it }
            Spacer(modifier = Modifier.height(12.dp))
            ProfileField("Email", editableEmail, isEditing) { editableEmail = it }
            Spacer(modifier = Modifier.height(12.dp))
            ProfileField("DOB", editableDob, isEditing) { editableDob = it }
            Spacer(modifier = Modifier.height(12.dp))
            ProfileField("Gender", editableGender, isEditing) { editableGender = it }
            Spacer(modifier = Modifier.height(12.dp))
            ProfileField("Address", editableAddress, isEditing) { editableAddress = it }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { isEditing = !isEditing },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                ) {
                    Text(if (isEditing) "Cancel Edit" else "Edit")
                }

                Button(
                    onClick = {
                        viewModel.updateProfile(
                            name = editableName,
                            bio = bio,
                            imageUri = imageUri,
                            email = editableEmail,
                            mobile = editableMobile,
                            dob = editableDob,
                            gender = editableGender,
                            address = editableAddress
                        )
                        isEditing = false
                    },
                    enabled = isEditing,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                ) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
fun ProfileField(
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White) },
        enabled = isEditing,
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
